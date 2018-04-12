package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;

public class TargetStatus extends Status {
	
	TargetStatus(UserService userService, String email) {
		super(userService, email);
	}
	
	ResponseEntity<String> getResponse() throws JSONException {
		if (!userExists) {
			return ResponseEntity.badRequest().body(new JSONObject()
            		.put(JsonStrings.MESSAGE, JsonStrings.TARGET_USER_NOT_FOUND)
            		.toString());
		}
		else {
			return null;
		}
	}
}
