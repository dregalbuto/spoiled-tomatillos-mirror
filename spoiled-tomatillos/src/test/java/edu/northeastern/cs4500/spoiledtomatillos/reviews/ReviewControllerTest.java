package edu.northeastern.cs4500.spoiledtomatillos.reviews;

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

import edu.northeastern.cs4500.spoiledtomatillos.Helper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;

/**
 * Test the external facing api for review.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

	private final String email = "test_ge@a.co";
	
	@Autowired
	MockMvc mockMvc;

	@Test
	public void postViewDeleteReview() throws Exception {
		JSONObject request = new JSONObject();
        request.put("first_name", "test_gf");
        request.put("last_name", "test_gl");
        request.put("email", email);
        request.put("username", "test_gu");
        request.put("password", "passw0rd");
        String token = Helper.signupLogin(request, mockMvc);
	
		// Post
		JSONObject postReq = new JSONObject();
		postReq.put("token", token);
		postReq.put("rating", "2");
		postReq.put("email", email);
		postReq.put("text", "Test review for a thingy");
		postReq.put("movieId", "tt0000001");
		String postId;
		String cont = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/post")
				.contentType(MediaType.APPLICATION_JSON).content(postReq.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println("PostId:|||||||\n" + cont);
		postId = new JSONObject(cont).getString("reviewId");

		// View
		JSONObject viewReq = new JSONObject();
		viewReq.put("postId", postId);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/get")
				.contentType(MediaType.APPLICATION_JSON).content(viewReq.toString()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.string("{\"id\":" +
						postId +
						",\"text\":\"Test review for a thingy\",\"rating\":2}"));

		// Delete
		JSONObject delReq = new JSONObject();
		delReq.put("token", token);
		delReq.put("email", email);
		delReq.put("postId", postId);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/delete")
				.contentType(MediaType.APPLICATION_JSON).content(delReq.toString()))
		.andExpect(MockMvcResultMatchers.status().isOk());




	}


}