package edu.northeastern.cs4500.spoiledtomatillos.groups;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String signupLogin(JSONObject per) throws Exception {
        // Signup
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()))
                .andDo(MockMvcResultHandlers.print());

        // Login
        String cont = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return new JSONObject(cont).getString("token");

    }

    private int count(String name) throws Exception {
        JSONObject search = new JSONObject();
        search.put("s", name);
        String ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/search")
                .contentType(MediaType.APPLICATION_JSON).content(search.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return new JSONArray(ret).length();
    }

    @Test
    public void createAndDeleteGroup() throws Exception {
        JSONObject request = new JSONObject();
        request.put("first_name", "test_gf");
        request.put("last_name", "test_gl");
        request.put("email", "test_ge@a.co");
        request.put("username", "test_gu");
        request.put("password", "passw0rd");
        String token1 = signupLogin(request);
        JSONObject request2 = new JSONObject();
        request2.put("first_name", "test_gf2");
        request2.put("last_name", "test_gl2");
        request2.put("email", "test_ge2@a.co");
        request2.put("username", "test_gu2");
        request2.put("password", "passw0rd");
        String token2 = signupLogin(request2);
        int count = count("TestingGroupName");

        //Create group
        JSONObject create = new JSONObject();
        create.put("email","test_ge@a.co");
        create.put("token", token1);
        create.put("blacklist","test_ge@a.co");
        create.put("groupName", "TestingGroupName");
        create.put("blacklist", "true");
        create.put("movieId", "tt0000001");
        String ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/create")
                .contentType(MediaType.APPLICATION_JSON).content(create.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        String groupId = new JSONObject(ret).getString("groupId");
        JSONObject group1 = new JSONObject();
        group1.put("email", "test_ge@a.co");
        group1.put("token", token1);
        group1.put("groupId", groupId);
        JSONObject group2 = new JSONObject();
        group2.put("email", "test_ge2@a.co");
        group2.put("token", token2);
        group2.put("groupId", groupId);

        //Get it
        ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\{\"id\":[0-9]+,\"name\":\"TestingGroupName\"," +
                "\"creator\":\\{\"id\":[0-9]+\\},\"users\":\\[\\],\"idList\":\\[\\]," +
                "\"topic\":\\{\"id\":\"tt0000001\"\\},\"reviews\":\\[\\],\"blacklist\":true\\}"));

        //Search
        assertEquals(count + 1, count("TestingGroupName"));

        //Add blacklist
        JSONObject bl = new JSONObject();
        bl.put("email", "test_ge@a.co");
        bl.put("token", token1);
        bl.put("groupId", groupId);
        bl.put("userEmail", "test_ge2@a.co");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(bl.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Join blacklisted
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/join")
                .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Get it
        ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\{\"id\":[0-9]+,\"name\":\"TestingGroupName\"," +
                "\"creator\":\\{\"id\":[0-9]+\\},\"users\":\\[\\],\"idList\":\\[[0-9]+\\]," +
                "\"topic\":\\{\"id\":\"tt0000001\"\\},\"reviews\":\\[\\],\"blacklist\":true\\}"));

        //Remove from blacklist
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/removefromlist")
                .contentType(MediaType.APPLICATION_JSON).content(bl.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Join
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/join")
                .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Post
        JSONObject post = new JSONObject();
        post.put("email", "test_ge@a.co");
        post.put("token", token1);
        post.put("groupId", groupId);
        post.put("rating", "2");
        post.put("text", "Wat");
        ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/post")
                .contentType(MediaType.APPLICATION_JSON).content(post.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONArray reviews = new JSONArray(new JSONObject(ret).get("reviews").toString());
        for (int i = 0; i < reviews.length(); i++) {
            // Delete
            JSONObject delReq = new JSONObject();
            delReq.put("email", "test_ge@a.co");
            delReq.put("token", token1);
            delReq.put("postId", reviews.getInt(i));
            this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/delete")
                    .contentType(MediaType.APPLICATION_JSON).content(delReq.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        //Leave
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/leave")
                .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Get it
        ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\{\"id\":[0-9]+,\"name\":\"TestingGroupName\"," +
                "\"creator\":\\{\"id\":[0-9]+\\},\"users\":\\[\\],\"idList\":\\[\\]," +
                "\"topic\":\\{\"id\":\"tt0000001\"\\},\"reviews\":\\[\\],\"blacklist\":true\\}"));

        //Delete it
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/delete")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Search
        assertEquals(count, count("TestingGroupName"));
    }

}