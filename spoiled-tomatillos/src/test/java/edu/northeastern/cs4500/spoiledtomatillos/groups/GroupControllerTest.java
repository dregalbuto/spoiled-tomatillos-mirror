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

        //Get request
        JSONObject friendlist1 = new JSONObject();
        friendlist1.put("email", "test_ge@a.co");
        friendlist1.put("token", token1);
        JSONObject friendlist2 = new JSONObject();
        friendlist2.put("email", "test_ge2@a.co");
        friendlist2.put("token", token2);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/search")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        // Sending request
        friendlist1.put("targetEmail", "test_ge2@a.co");
        friendlist2.put("targetEmail", "test_ge@a.co");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Success\"}"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        String cont = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(1, new JSONArray(cont).length());

        // Rejecting request
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/reject")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        // Sending request
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Success\"}"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        String cont2 = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(1, new JSONArray(cont2).length());

        // Accepting request
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/accept")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        String cont3 = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(1, new JSONArray(cont3).length());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        String cont4 = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(1, new JSONArray(cont4).length());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        // Unfriend request
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/unfriend")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Success\"}"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
                .contentType(MediaType.APPLICATION_JSON).content(friendlist2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}