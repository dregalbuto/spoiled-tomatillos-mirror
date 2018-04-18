package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.Helper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the external facing api for review.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    private static final String EMAIL1 = "test_ge@a.co";
    private static final String EMAIL2 = "test_ge2@a.co";
    private static final String MOVIE_ID = "tt0000001";

    @Autowired
    MockMvc mockMvc;

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
    public void postViewDeleteReviewAdditional() throws Exception {
        // Login
        JSONObject loginJson = new JSONObject();
        loginJson.put("first_name", "erin");
        loginJson.put("last_name", "zhang");
        loginJson.put("email", "erinzhang@husky.neu.edu");
        loginJson.put("username", "erin.z");
        loginJson.put("password", "passw0rd");
        String token = signupLogin(loginJson);

        // Post
        JSONObject postReq = new JSONObject();
        postReq.put("token", token);
        postReq.put("rating", "2");
        postReq.put("email", "erinzhang@husky.neu.edu");
        postReq.put("text", "Test review for a thingy");
        postReq.put("movieId", "tt0000001");
        String postId;
        String cont = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/post")
                .contentType(MediaType.APPLICATION_JSON).content(postReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        postId = new JSONObject(cont).getString(JsonStrings.REVIEW_ID);
        // View
        JSONObject viewReq = new JSONObject();
        viewReq.put("reviewId", postId);
        String str = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/get")
                .contentType(MediaType.APPLICATION_JSON).content(viewReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(str.startsWith("{\"id\":" +
                postId +
                ",\"text\":\"Test review for a thingy\",\"rating\":2," +
                "\"movie\":{\"id\":\"tt0000001\",\"title\":\"Carmencita\"},\"critic\":false,\"userId\":"));
        // View postId
        JSONObject viewReqP = new JSONObject();
        viewReqP.put("postId", postId);
        str = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/get")
                .contentType(MediaType.APPLICATION_JSON).content(viewReqP.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(str.startsWith("{\"id\":" +
                postId +
                ",\"text\":\"Test review for a thingy\",\"rating\":2," +
                "\"movie\":{\"id\":\"tt0000001\",\"title\":\"Carmencita\"},\"critic\":false,\"userId\":"));

        // Delete
        JSONObject delReq = new JSONObject();
        delReq.put("token", token);
        delReq.put("email", "erinzhang@husky.neu.edu");
        delReq.put("reviewId", postId);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/delete")
                .contentType(MediaType.APPLICATION_JSON).content(delReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // View none existent post
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/get")
                .contentType(MediaType.APPLICATION_JSON).content(viewReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private JSONObject postReview(String email, String token
            , String movieId, Boolean expectOk)
            throws Exception {
        JSONObject postReq = new JSONObject();
        postReq.put(JsonStrings.EMAIL, email);
        postReq.put(JsonStrings.TOKEN, token);
        postReq.put(JsonStrings.MOVIE_ID, movieId);
        postReq.put(JsonStrings.RATING, "2");
        postReq.put(JsonStrings.TEXT, "Test review for a thingy");
        if (expectOk) {
            return new JSONObject(this.mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/reviews/post")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postReq.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString());
        } else {
            return new JSONObject(this.mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/reviews/post")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postReq.toString()))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn().getResponse().getContentAsString());
        }
    }

    private JSONObject deleteReview(String email, String token, String postId
            , boolean expectOk) throws Exception {
        JSONObject postReq = new JSONObject();
        postReq.put(JsonStrings.EMAIL, email);
        postReq.put(JsonStrings.TOKEN, token);
        postReq.put(JsonStrings.REVIEW_ID, postId);
        if (expectOk) {
            return new JSONObject(this.mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/reviews/delete")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postReq.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString());
        } else {
            return new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/reviews/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(postReq.toString()))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void postReviewInvalidUser() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = postReview("BAD_EMAIL", token, MOVIE_ID, false);
        assertEquals(JsonStrings.USER_NOT_FOUND
                , response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void postReviewBadToken() throws Exception {
        Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = postReview(EMAIL1, "BAD_TOKEN", MOVIE_ID, false);
        assertEquals(JsonStrings.TOKEN_EXPIRED
                , response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void postReviewBadMovie() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = postReview(EMAIL1, token, "BAD_ID", false);
        assertEquals(JsonStrings.MOVIE_NOT_FOUND
                , response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteReviewInvalidUser() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = postReview(EMAIL1, token, MOVIE_ID, true);
        String reviewId = response.getString(JsonStrings.REVIEW_ID);
        JSONObject response2 = deleteReview("BAD_EMAIL", token, reviewId, false);
        assertEquals(JsonStrings.USER_NOT_FOUND
                , response2.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteReviewBadToken() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        JSONObject response = postReview(EMAIL1, token, MOVIE_ID, true);
        String reviewId = response.getString(JsonStrings.REVIEW_ID);
        JSONObject response2 = deleteReview(EMAIL1, "BAD_TOKEN", reviewId, false);
        assertEquals(JsonStrings.TOKEN_EXPIRED
                , response2.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteReviewBadId() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        postReview(EMAIL1, token, MOVIE_ID, true);
        JSONObject response2 = deleteReview(EMAIL1, token, "BAD_ID", false);
        assertEquals(JsonStrings.REVIEW_NOT_FOUND
                , response2.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteReviewBadId2() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        postReview(EMAIL1, token, MOVIE_ID, true);
        JSONObject response2 = deleteReview(EMAIL1, token, "-1", false);
        assertEquals(JsonStrings.REVIEW_NOT_FOUND
                , response2.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void deleteReviewNotAuthor() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);
        JSONObject response = postReview(EMAIL1, token, MOVIE_ID, true);
        String reviewId = response.getString(JsonStrings.REVIEW_ID);
        JSONObject response2 = deleteReview(EMAIL2, token2, reviewId, false);
        assertEquals(JsonStrings.NO_PERMISSION
                , response2.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void getReviewBadId() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        postReview(EMAIL1, token, MOVIE_ID, true);
        JSONObject viewReq = new JSONObject();
        viewReq.put(JsonStrings.REVIEW_ID, "BAD_ID");
        JSONObject response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/reviews/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(viewReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.REVIEW_NOT_FOUND
                , response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void getReviewBadId2() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);
        postReview(EMAIL1, token, MOVIE_ID, true);
        JSONObject viewReq = new JSONObject();
        viewReq.put(JsonStrings.REVIEW_ID, "-1");
        JSONObject response = new JSONObject(this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/reviews/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(viewReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.REVIEW_NOT_FOUND
                , response.getString(JsonStrings.MESSAGE));
    }

    @Test
    public void postViewDeleteReview() throws Exception {
        String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);

        // Post
        JSONObject postReq = new JSONObject();
        postReq.put(JsonStrings.TOKEN, token);
        postReq.put(JsonStrings.RATING, "2");
        postReq.put(JsonStrings.EMAIL, EMAIL1);
        postReq.put(JsonStrings.TEXT, "Test review for a thingy");
        postReq.put(JsonStrings.MOVIE_ID, "tt0000001");

        String cont = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reviews/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        String reviewId = new JSONObject(cont).getString("reviewId");

        // View
        JSONObject viewReq = new JSONObject();
        viewReq.put(JsonStrings.REVIEW_ID, reviewId);
        String str = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(viewReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(str.startsWith("{\"id\":" +
                reviewId +
                ",\"text\":\"Test review for a thingy\",\"rating\":2" +
                ",\"movie\":{\"id\":\"tt0000001\",\"title\":\"Carmencita\"}," +
                "\"critic\":false,\"userId\":"));

        // Delete
        JSONObject delReq = new JSONObject();
        delReq.put(JsonStrings.TOKEN, token);
        delReq.put(JsonStrings.EMAIL, EMAIL1);
        delReq.put(JsonStrings.REVIEW_ID, reviewId);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(delReq.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}