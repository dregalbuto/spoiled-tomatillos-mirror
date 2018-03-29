package edu.northeastern.cs4500.spoiledtomatillos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;


@RestController
@RequestMapping(value = "/api/friend")
public class FriendController {
	
	private class Helper {
		ResponseEntity<String> sourceResponse;
		ResponseEntity<String> targetResponse;
		User source;
		User target;
		Helper(String strRequest) throws JSONException {
			sourceResponse = targetResponse = null;
			JSONObject request = new JSONObject(strRequest);
			String email = request.getString(JsonStrings.EMAIL);
			String token = request.getString(JsonStrings.TOKEN);
			Status sourceStatus = new UserStatus(userService, email, token);
			if (sourceStatus.getResponse() != null) {
				sourceResponse = sourceStatus.getResponse();
			}
			source = sourceStatus.getUser();
			if (request.has(JsonStrings.TARGET_EMAIL)) {
				String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
				Status targetStatus = new TargetStatus(userService, targetEmail);
	
				target = targetStatus.getUser();
				if (targetStatus.getResponse() != null) {
					targetResponse = targetStatus.getResponse();
				}
			}
		}
	}
	
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity<String> sendFriendRequest(@RequestBody String strRequest) 
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		if (h.targetResponse != null) return h.targetResponse;
		boolean success = h.target.getFriends().addRequest(h.source);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject()
					.put(JsonStrings.MESSAGE, JsonStrings.ERROR)
					.toString());
		}
		this.userService.save(h.target);
		this.userService.save(h.source);
		return ResponseEntity.ok().body(
				new JSONObject()
				.put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.toString());
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public ResponseEntity<String> acceptFriendRequest(@RequestBody String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		if (h.targetResponse != null) return h.targetResponse;
		boolean success = h.source.getFriends().acceptRequest(h.target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
		this.userService.save(h.source);
		this.userService.save(h.target);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ResponseEntity<String> rejectFriendRequest(@RequestBody String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		if (h.targetResponse != null) return h.targetResponse;
		boolean success = h.source.getFriends().rejectRequest(h.target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
		this.userService.save(h.source);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

	}

	@RequestMapping(value = "/unfriend", method = RequestMethod.POST)
	public ResponseEntity<String> unfriend(@RequestBody String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		if (h.targetResponse != null) return h.targetResponse;
		boolean success = h.source.getFriends().removeFriend(h.target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject()
					.put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
		this.userService.save(h.source);
		this.userService.save(h.target);
		return ResponseEntity.ok().body(
				new JSONObject()
				.put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

	}

	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	public ResponseEntity<String> list(@RequestBody String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		try {
			return ResponseEntity.ok().body(
					new ObjectMapper().writeValueAsString(
							h.source.getFriends().getFriends()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.ERROR).toString());
		}
	}

	@RequestMapping(value = "/requests", method = RequestMethod.POST)
	public ResponseEntity<String> request(@RequestBody String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.sourceResponse != null) return h.sourceResponse;
		try {
			return ResponseEntity.ok().body(
					new ObjectMapper().writeValueAsString(
							h.source.getFriends().getRequests()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
	}
}
