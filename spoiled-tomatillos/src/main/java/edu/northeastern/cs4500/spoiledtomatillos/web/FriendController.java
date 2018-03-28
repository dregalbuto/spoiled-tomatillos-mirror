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

	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity<String> sendFriendRequest(@RequestBody String strRequest) 
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        Status targetStatus = new TargetStatus(userService, targetEmail);
        if (targetStatus.getResponse() != null) {
    			return targetStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
        User target = targetStatus.getUser();
		boolean success = target.getFriends().addRequest(source);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.ERROR).toString());
		}
		this.userService.save(target);
		this.userService.save(source);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public ResponseEntity<String> acceptFriendRequest(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        Status targetStatus = new TargetStatus(userService, targetEmail);
        if (targetStatus.getResponse() != null) {
    			return targetStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
        User target = targetStatus.getUser();
		boolean success = source.getFriends().acceptRequest(target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
		this.userService.save(source);
		this.userService.save(target);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ResponseEntity<String> rejectFriendRequest(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        Status targetStatus = new TargetStatus(userService, targetEmail);
        if (targetStatus.getResponse() != null) {
    			return targetStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
        User target = targetStatus.getUser();
		boolean success = source.getFriends().rejectRequest(target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
		this.userService.save(source);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

	}

	@RequestMapping(value = "/unfriend", method = RequestMethod.POST)
	public ResponseEntity<String> unfriend(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        TargetStatus targetStatus = new TargetStatus(userService, targetEmail);
        if (targetStatus.getResponse() != null) {
    			return targetStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
        User target = targetStatus.getUser();
		boolean success = source.getFriends().removeFriend(target);
		if (!success) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.ERROR).toString());
		}
		this.userService.save(source);
		this.userService.save(target);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE
						, JsonStrings.SUCCESS).toString());

	}

	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	public ResponseEntity<String> list(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
		try {
			return ResponseEntity.ok().body(
					new ObjectMapper().writeValueAsString(
							source.getFriends().getFriends()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.ERROR).toString());
		}
	}

	@RequestMapping(value = "/requests", method = RequestMethod.POST)
	public ResponseEntity<String> request(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		
		Status sourceStatus = new UserStatus(userService, email, token);
        if (sourceStatus.getResponse() != null) {
        		return sourceStatus.getResponse();
        }
        
        User source = sourceStatus.getUser();
		try {
			return ResponseEntity.ok().body(
					new ObjectMapper().writeValueAsString(
							source.getFriends().getRequests()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
		}
	}
}
