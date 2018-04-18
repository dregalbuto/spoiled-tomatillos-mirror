package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.Helper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {

    // Constants for testing
    private static final String EMAIL1 = "test_ge@a.co";
    private static final String EMAIL2 = "test_ge2@a.co";
    private static final String MOVIE_ID = "tt0000001";
    @Autowired
    private MockMvc mockMvc;

    // Helper functions
    private int count(String name) throws Exception {
        JSONObject search = new JSONObject();
        search.put("s", name);
        String ret = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/search")
                        .contentType(MediaType.APPLICATION_JSON).content(search.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return new JSONArray(ret).length();
    }

    private String createGroup(String email, String token, String movieId, boolean expectOk)
            throws Exception {
        //Create group
        JSONObject create = new JSONObject();
        create.put("email", email);
        create.put("token", token);
        create.put("blacklist", "test_ge@a.co");
        create.put("groupName", "TestingGroupName");
        create.put("blacklist", "true");
        create.put("movieId", movieId);
        if (expectOk) {
            return this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/create")
                    .contentType(MediaType.APPLICATION_JSON).content(create.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();

        } else {
            return this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/create")
                    .contentType(MediaType.APPLICATION_JSON).content(create.toString()))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
        }

    }

    private JSONObject createGroupJson(String email, String token, String movieId, boolean expectOk)
            throws Exception {
        return new JSONObject(createGroup(email, token, movieId, expectOk));

    }

    private void deleteGroup(String email, String token, String groupId) throws Exception {
        JSONObject group2 = new JSONObject();
        group2.put("email", email);
        group2.put("token", token);
        group2.put("groupId", groupId);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Tests
    @Test
    public void createGroupBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = createGroupJson("badEmail", token1, MOVIE_ID, false);
        assertEquals(JsonStrings.INVALID_LOGIN, response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void createGroupInvalidMovieId() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, "BADID", false);
        assertEquals(JsonStrings.MOVIE_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteGroupBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group, but invalid login email
        JSONObject group1 = new JSONObject();
        group1.put("email", "BAD_EMAIL");
        group1.put("token", token1);
        group1.put("groupId", groupId);

        //Attempt to delete it
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, response.getString(JsonStrings.MESSAGE));

        //Actually delete it
        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void deleteGroupNotCreator() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);

        //Create group as first user
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group, but wrong user
        JSONObject group1 = new JSONObject();
        group1.put("email", EMAIL2);
        group1.put("token", token2);
        group1.put("groupId", groupId);

        //Attempt to delete it
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/delete")
                        .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.NO_PERMISSION, response.getString(JsonStrings.MESSAGE));

        //Actually delete it
        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void joinGroupBadLogin() throws Exception {
        // Create and login
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group
        JSONObject group2 = new JSONObject();
        group2.put("email", "test_ge@a.co");
        group2.put("token", "INVALID_TOKEN");
        group2.put("groupId", groupId);

        // Attempt to join
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/join")
                        .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, response.getString(JsonStrings.MESSAGE));

        //Actually delete it
        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void joinGroupAlreadyMember() throws Exception {
        // Create and login
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group
        JSONObject group2 = new JSONObject();
        group2.put("email", "test_ge@a.co");
        group2.put("token", token1);
        group2.put("groupId", groupId);

        // Attempt to join
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/join")
                        .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.CANNOT_JOIN, response.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void leaveGroupBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group, other user
        JSONObject group1 = new JSONObject();
        group1.put("email", "test_ge2@a.co");
        group1.put("token", token2);
        group1.put("groupId", groupId);

        // Same group, other user but invalid login
        JSONObject group2 = new JSONObject();
        group2.put("email", "test_ge2@a.co");
        group2.put("token", "BAD_TOKEN");
        group2.put("groupId", groupId);

        //Join
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/join")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Attempt to leave
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/leave")
                        .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, response.getString(JsonStrings.MESSAGE));

        //Actually leave
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/leave")
                        .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        deleteGroup(EMAIL1, token1, groupId);
    }

    public void leaveGroupNotMember() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString("groupId");

        // Valid group, other user
        JSONObject group1 = new JSONObject();
        group1.put("email", "test_ge2@a.co");
        group1.put("token", token2);
        group1.put("groupId", groupId);

        //Attempt to leave when not a member
        response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/groups/leave")
                        .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.CANNOT_LEAVE, response.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void postBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        JSONObject post = new JSONObject();
        post.put("email", EMAIL1);
        post.put("token", "BAD_TOKEN");
        post.put("groupId", groupId);
        post.put("rating", "2");
        post.put("text", "Wat");
        JSONObject postResponse = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/post")
                .contentType(MediaType.APPLICATION_JSON).content(post.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, postResponse.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void postNotInGroup() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        JSONObject post = new JSONObject();
        post.put("email", EMAIL2);
        post.put("token", token2);
        post.put("token", token2);
        post.put("groupId", groupId);
        post.put("rating", "2");
        post.put("text", "Wat");
        JSONObject postResponse = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/post")
                .contentType(MediaType.APPLICATION_JSON).content(post.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.NO_PERMISSION, postResponse.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void getBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        JSONObject group1 = new JSONObject();
        group1.put("email", EMAIL1);
        group1.put("token", "BAD_TOKEN");
        group1.put("groupId", groupId);

        JSONObject resp = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, resp.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void addBadLogin() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        JSONObject group1 = new JSONObject();
        group1.put("email", EMAIL1);
        group1.put("token", "BAD_TOKEN");
        group1.put("groupId", groupId);

        JSONObject resp = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.INVALID_LOGIN, resp.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void getNotInGroup() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        JSONObject group1 = new JSONObject();
        group1.put("email", EMAIL2);
        group1.put("token", token2);
        group1.put("groupId", groupId);

        JSONObject resp = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.NO_PERMISSION, resp.getString(JsonStrings.MESSAGE));

        deleteGroup(EMAIL1, token1, groupId);
    }

    @Test
    public void createAndDeleteGroup() throws Exception {
        String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);
        int count = count("TestingGroupName");

        //Create group
        JSONObject response = createGroupJson(EMAIL1, token1, MOVIE_ID, true);
        String groupId = response.getString(JsonStrings.GROUP_ID);

        // Valid group
        JSONObject group1 = new JSONObject();
        group1.put("email", EMAIL1);
        group1.put("token", token1);
        group1.put("groupId", groupId);

        // Blacklisted group
        JSONObject group2 = new JSONObject();
        group2.put("email", EMAIL2);
        group2.put("token", token2);
        group2.put("groupId", groupId);

        //Get it
        String ret = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/get")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\{\"id\":[0-9]+,\"name\":\"TestingGroupName\"," +
                "\"creator\":\\{\"id\":[0-9]+\\},\"users\":\\[\\],\"idList\":\\[\\]," +
                "\"topic\":\\{\"id\":\"tt0000001\"\\},\"reviews\":\\[\\],\"blacklist\":true\\}"));

        //Search
        assertEquals(count + 1, count("TestingGroupName"));

        //Add to blacklist bad login
        JSONObject blnbl = new JSONObject();
        blnbl.put("email", "test_ge2@a.co");
        blnbl.put("token", token1);
        blnbl.put("groupId", groupId);
        blnbl.put(JsonStrings.TARGET_EMAIL, "test_ge2@a.co");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(blnbl.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //Remove from bad login
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/removefromlist")
                .contentType(MediaType.APPLICATION_JSON).content(blnbl.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Add to blacklist user not found
        JSONObject targetUserNotFound = new JSONObject();
        targetUserNotFound.put("email", "test_ge2@a.co");
        targetUserNotFound.put("token", token1);
        targetUserNotFound.put("groupId", groupId);
        targetUserNotFound.put(JsonStrings.TARGET_EMAIL, "BAD_TARGET_EMAIL");
        JSONObject myResponse = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(targetUserNotFound.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.TARGET_USER_NOT_FOUND, myResponse.get(JsonStrings.MESSAGE));


        //Add to blacklist no perm
        JSONObject blnp = new JSONObject();
        blnp.put("email", "test_ge2@a.co");
        blnp.put("token", token2);
        blnp.put("groupId", groupId);
        blnp.put(JsonStrings.TARGET_EMAIL, "test_ge2@a.co");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(blnp.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //Remove from no perm
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/removefromlist")
                .contentType(MediaType.APPLICATION_JSON).content(blnp.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Add to blacklist
        JSONObject bl = new JSONObject();
        bl.put("email", "test_ge@a.co");
        bl.put("token", token1);
        bl.put("groupId", groupId);
        bl.put(JsonStrings.TARGET_EMAIL, "test_ge2@a.co");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(bl.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Add again
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/addtolist")
                .contentType(MediaType.APPLICATION_JSON).content(bl.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

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

        ret = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/email/test_ge2@a.co/groups"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", ret);

        //Remove again
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/removefromlist")
                .contentType(MediaType.APPLICATION_JSON).content(bl.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

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

        ret = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/email/test_ge@a.co/groups"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\[\\{\"id\"\\:[0-9]+,\"name\"\\:\"TestingGroupName\"," +
                "\"creator\"\\:\\{\"id\"\\:[0-9]+\\},\"users\"\\:\\[[0-9]+\\],\"idList\"\\:\\[\\]," +
                "\"topic\"\\:\\{\"id\"\\:\"tt0000001\"\\},\"reviews\"\\:\\[\\],\"blacklist\"\\:true\\}\\]"));

        ret = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/email/test_ge2@a.co/groups"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.matches("\\[\\{\"id\"\\:[0-9]+,\"name\"\\:\"TestingGroupName\"," +
                "\"creator\"\\:\\{\"id\"\\:[0-9]+\\},\"users\"\\:\\[[0-9]+\\],\"idList\"\\:\\[\\]," +
                "\"topic\"\\:\\{\"id\"\\:\"tt0000001\"\\},\"reviews\"\\:\\[\\],\"blacklist\"\\:true\\}\\]"));

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

        //Leave
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/leave")
                .contentType(MediaType.APPLICATION_JSON).content(group2.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Delete it
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/delete")
                .contentType(MediaType.APPLICATION_JSON).content(group1.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Search
        assertEquals(count, count("TestingGroupName"));

        //Search
        assertEquals(0, count("RandomRadomlongasfthing"));
    }

}