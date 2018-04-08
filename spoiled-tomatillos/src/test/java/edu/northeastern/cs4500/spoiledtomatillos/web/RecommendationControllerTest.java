package edu.northeastern.cs4500.spoiledtomatillos.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.northeastern.cs4500.spoiledtomatillos.Helper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecommendationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private final String LOGGED_IN = "test@husky.neu.edu";
	private final String TARGET = "kate@neu.edu";
	private final String MOVIE = "tt0046003";
	private final String REC_MESSAGE = "You'll really love this movie!";
	
	@Test
	public void addErrors() throws Exception {
		Helper.signupLoginDefaults(LOGGED_IN, mockMvc);
		
		// Check if the target (receiver) user is valid
		JSONObject object1 = new JSONObject();
		object1.put(JsonStrings.TARGET_EMAIL, "BAD_EMAIL");
		JSONObject response1 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.TARGET_USER_NOT_FOUND, response1.get(JsonStrings.MESSAGE));
		
		// Check if the movie is valid 
		JSONObject object2 = new JSONObject();
		object2.put(JsonStrings.MOVIE_ID, "BAD MOVIE ID");
		JSONObject response2 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object2.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.MOVIE_NOT_FOUND, response2.get(JsonStrings.MESSAGE));
		
		// Check if the logged-in (sender) user is present and valid
		JSONObject object3 = new JSONObject();
		object3.put(JsonStrings.EMAIL,LOGGED_IN);
		object3.put(JsonStrings.TOKEN, "BAD_TOKEN");
		JSONObject response3 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object3.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.INVALID_LOGIN, response3.get(JsonStrings.MESSAGE));
	}
	
	@Test
	public void deleteErrors() throws Exception {
		Helper.signupLoginDefaults(LOGGED_IN, mockMvc);
		
		// Check if the target (receiver) user is valid
		JSONObject object1 = new JSONObject();
		object1.put(JsonStrings.TARGET_EMAIL, LOGGED_IN);
		object1.put(JsonStrings.MOVIE_ID, MOVIE);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object1.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		
		// Check if the logged-in (deleter) user is present and valid
		JSONObject object2 = new JSONObject();
		object2.put(JsonStrings.EMAIL,LOGGED_IN);
		object2.put(JsonStrings.TOKEN, "BAD_TOKEN");
		JSONObject response2 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object2.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.INVALID_LOGIN, response2.get(JsonStrings.MESSAGE));
	}
	
	@Test
	public void getErrors() throws Exception {
		Helper.signupLoginDefaults(LOGGED_IN, mockMvc);
		
		// Check if the target (getter) user is valid
		JSONObject object1 = new JSONObject();
		object1.put(JsonStrings.EMAIL, LOGGED_IN);
		object1.put(JsonStrings.TOKEN, "BAD_TOKEN");
		JSONObject response = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.INVALID_LOGIN, response.get(JsonStrings.MESSAGE));
	}
	
	@Test
	public void addGetDelete() throws Exception {
		String token = Helper.signupLoginDefaults(LOGGED_IN, mockMvc);
		String token2 = Helper.signupLoginDefaults(TARGET, mockMvc);
		
		JSONObject create = new JSONObject();
		create.put(JsonStrings.EMAIL, LOGGED_IN);
		create.put(JsonStrings.TOKEN, token);
		create.put(JsonStrings.MOVIE_ID, MOVIE);
		create.put(JsonStrings.TARGET_EMAIL, TARGET);
		create.put(JsonStrings.REC_MESSAGE, REC_MESSAGE);
		JSONObject response1 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(create.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		System.out.println(response1);
		String recId1 = response1.getString(JsonStrings.REC_ID);
		
		JSONObject createNoSourceUser = new JSONObject();
		createNoSourceUser.put(JsonStrings.MOVIE_ID, MOVIE);
		createNoSourceUser.put(JsonStrings.TARGET_EMAIL, TARGET);
		JSONObject response2 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createNoSourceUser.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		String recId2 = response2.getString(JsonStrings.REC_ID);
		
		JSONObject getRecs = new JSONObject();
		getRecs.put(JsonStrings.EMAIL, TARGET);
		getRecs.put(JsonStrings.TOKEN, token2);
		JSONArray response3 = new JSONArray(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/get")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		
		assertEquals(recId1, response3.getJSONObject(0).get(JsonStrings.REC_ID).toString());
		assertEquals(LOGGED_IN, response3.getJSONObject(0).getJSONObject("recommendationGiver").get("email"));
		assertEquals(recId2, response3.getJSONObject(1).get(JsonStrings.REC_ID).toString());
		assertTrue(response3.getJSONObject(1).isNull("recommendationGiver"));
		
		getRecs.put(JsonStrings.REC_ID, recId1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		getRecs.put(JsonStrings.REC_ID, recId2);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
