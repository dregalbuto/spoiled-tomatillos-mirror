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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class FriendControllerTest {

	@Autowired
	private MockMvc mockMvc;

	//Constants for testing
	private static final String EMAIL1 = "test_se@a.co";
	private static final String EMAIL2 = "test_se2@a.co";

	@Test
	public void sendBadLogin() throws Exception {
		Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", "BAD_TOKEN");
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		// Sending request
		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.TOKEN_EXPIRED, response.getString(JsonStrings.MESSAGE));
	}
	
	@Test
	public void sendBadLogin2() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", "BAD_EMAIL");
		friendlist1.put("token", token1);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		// Sending request
		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void sendFriendNotFound() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", token1);
		friendlist1.put(JsonStrings.TARGET_EMAIL, "BAD_EMAIL");

		// Sending request
		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.TARGET_USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void sendFriendErrorDuplicate() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
		Helper.signupLoginDefaults(EMAIL2, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", token1);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		// Sending request
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
		.andExpect(MockMvcResultMatchers.status().isOk());

		// Sending request again
		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/send")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
		assertEquals(JsonStrings.ERROR, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void rejectInvalidUser() throws Exception {
		Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", "BAD_TOKEN");
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/reject")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.TOKEN_EXPIRED, response.getString(JsonStrings.MESSAGE));
	}
	
	@Test
	public void rejectInvalidUser2() throws Exception {
		String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", "BAD_EMAIL");
		friendlist1.put("token", token);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/reject")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void rejectError() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", token1);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/reject")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.ERROR, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void unfriendInvalidLogin() throws Exception {
		Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", "BAD_TOKEN");
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/unfriend")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.TOKEN_EXPIRED, response.getString(JsonStrings.MESSAGE));
	}
	
	@Test
	public void unfriendInvalidLogin2() throws Exception {
		String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", "BAD_EMAIL");
		friendlist1.put("token", token);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/unfriend")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void unfriendTargetNotFound() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", token1);
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/unfriend")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.TARGET_USER_NOT_FOUND
				, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void getFriendsInvalidUser() throws Exception {
		Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", "BAD_TOKEN");

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.TOKEN_EXPIRED, response.getString(JsonStrings.MESSAGE));
	}
	
	@Test
	public void getFriendsInvalidUser2() throws Exception {
		String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", "BAD_EMAIL");
		friendlist1.put("token", token);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/friends")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void getRequestsInvalidUser() throws Exception {
		Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", "BAD_TOKEN");

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.TOKEN_EXPIRED, response.getString(JsonStrings.MESSAGE));
	}
	
	@Test
	public void getRequestsInvalidUser2() throws Exception {
		String token = Helper.signupLoginDefaults(EMAIL1, mockMvc);

		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", "BAD_EMAIL");
		friendlist1.put("token", token);

		JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/friend/requests")
				.contentType(MediaType.APPLICATION_JSON).content(friendlist1.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());

		assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
	}

	@Test
	public void sendFriendRequest() throws Exception {
		String token1 = Helper.signupLoginDefaults(EMAIL1, mockMvc);
		String token2 = Helper.signupLoginDefaults(EMAIL2, mockMvc);

		//Get request
		JSONObject friendlist1 = new JSONObject();
		friendlist1.put("email", EMAIL1);
		friendlist1.put("token", token1);
		JSONObject friendlist2 = new JSONObject();
		friendlist2.put("email", EMAIL2);
		friendlist2.put("token", token2);
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
		friendlist1.put(JsonStrings.TARGET_EMAIL, EMAIL2);
		friendlist2.put(JsonStrings.TARGET_EMAIL, EMAIL1);
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