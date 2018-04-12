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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.northeastern.cs4500.spoiledtomatillos.Helper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private final String LOGGED_IN1 = "test@husky.neu.edu";
	private final String LOGGED_IN2 = "test2@husky.neu.edu";
	private final String LOGGED_IN3 = "test3@husky.neu.edu";
	private final String LOGGED_IN4 = "test4@husky.neu.edu";
	private final String LOGGED_IN5 = "test5@husky.neu.edu";
	private final String TARGET = "target@husky.neu.edu";
	private final String MOVIE = "tt0046003";
	private final String REC_MESSAGE = "You'll really love this movie!";
	
	@Test
	public void addErrors() throws Exception {
		Helper.signupLoginDefaults(LOGGED_IN1, mockMvc);
		
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
		object3.put(JsonStrings.EMAIL,LOGGED_IN1);
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
		String token = Helper.signupLoginDefaults(LOGGED_IN2, mockMvc);
		
		// Check if the target (receiver) user is valid
		JSONObject object1 = new JSONObject();
		object1.put(JsonStrings.TARGET_EMAIL, LOGGED_IN2);
		object1.put(JsonStrings.MOVIE_ID, MOVIE);
		JSONObject response1 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object1.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		String recId = response1.getString(JsonStrings.REC_ID);
		
		// Check if the logged-in (deleter) user is present and valid
		JSONObject object2 = new JSONObject();
		object2.put(JsonStrings.EMAIL,LOGGED_IN2);
		object2.put(JsonStrings.TOKEN, "BAD_TOKEN");
		JSONObject response2 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object2.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.INVALID_LOGIN, response2.get(JsonStrings.MESSAGE));
		
		// Try to delete the request but give an invalid rec ID
		JSONObject object3 = new JSONObject();
		object3.put(JsonStrings.EMAIL,LOGGED_IN2);
		object3.put(JsonStrings.TOKEN, token);
		object3.put(JsonStrings.REC_ID, "666");
		JSONObject response3 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object3.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.REC_NOT_FOUND, response3.get(JsonStrings.MESSAGE));
		
		// Actually delete the request
		JSONObject object4 = new JSONObject();
		object4.put(JsonStrings.EMAIL,LOGGED_IN2);
		object4.put(JsonStrings.TOKEN, token);
		object4.put(JsonStrings.REC_ID, recId);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object4.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getErrors() throws Exception {
		Helper.signupLoginDefaults(LOGGED_IN3, mockMvc);
		
		// Check if the target (getter) user is valid
		JSONObject object1 = new JSONObject();
		object1.put(JsonStrings.TARGET_EMAIL, LOGGED_IN3);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object1.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		// Check if the logged-in (getter) user is present and valid
		JSONObject object2 = new JSONObject();
		object2.put(JsonStrings.EMAIL,LOGGED_IN3);
		object2.put(JsonStrings.TOKEN, "BAD_TOKEN");
		JSONObject response3 = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/get")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object2.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.INVALID_LOGIN, response3.get(JsonStrings.MESSAGE));
	}
	
	@Test
	public void addGetDelete() throws Exception {
		String token = Helper.signupLoginDefaults(LOGGED_IN4, mockMvc);
		String token2 = Helper.signupLoginDefaults(TARGET, mockMvc);
		String token3 = Helper.signupLoginDefaults(LOGGED_IN5, mockMvc);
		
		JSONObject create = new JSONObject();
		create.put(JsonStrings.EMAIL, LOGGED_IN4);
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
		
		JSONObject createSomeoneElse = new JSONObject();
		createSomeoneElse.put(JsonStrings.EMAIL, LOGGED_IN4);
		createSomeoneElse.put(JsonStrings.TOKEN, token);
		createSomeoneElse.put(JsonStrings.MOVIE_ID, MOVIE);
		createSomeoneElse.put(JsonStrings.TARGET_EMAIL, LOGGED_IN5);
		createSomeoneElse.put(JsonStrings.REC_MESSAGE, REC_MESSAGE);
		String recIdSomeoneElse = new JSONObject(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createSomeoneElse.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString())
				.getString(JsonStrings.REC_ID);
		
		JSONObject getRecs = new JSONObject();
		getRecs.put(JsonStrings.EMAIL, TARGET);
		getRecs.put(JsonStrings.TOKEN, token2);
		JSONArray response3 = new JSONArray(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/get")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		
		assertEquals(2, response3.length());
		assertEquals(recId1, response3.getJSONObject(0).get(JsonStrings.REC_ID).toString());
		assertEquals(LOGGED_IN4, response3.getJSONObject(0).get("sender").toString());
		assertEquals(recId2, response3.getJSONObject(1).get(JsonStrings.REC_ID).toString());
		assertTrue(response3.getJSONObject(1).isNull("sender"));
		
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
		
		JSONObject getRecs2 = new JSONObject();
		getRecs2.put(JsonStrings.EMAIL, LOGGED_IN5);
		getRecs2.put(JsonStrings.TOKEN, token3);
		response3 = new JSONArray(this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/recommendations/get")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs2.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString());
		assertEquals(1, response3.length());		
		assertEquals(recIdSomeoneElse, 
				response3.getJSONObject(0).get(JsonStrings.REC_ID).toString());
		
		getRecs2.put(JsonStrings.REC_ID, recIdSomeoneElse);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendations/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getRecs2.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
}
