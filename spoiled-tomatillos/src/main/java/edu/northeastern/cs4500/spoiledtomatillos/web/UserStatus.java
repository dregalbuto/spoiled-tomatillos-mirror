package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;

public class UserStatus extends Status {
	
	boolean tokenExpired;
	
	UserStatus(UserService userService, String email, String token) {
		super(userService, email);
		tokenExpired = true;
		tokenExpired = (user != null) && (!user.validToken(token));
	}

	@Override
	ResponseEntity<String> getResponse() throws JSONException {
		if (!userExists) {
			return ResponseEntity.badRequest().body(new JSONObject()
            		.put(JsonStrings.MESSAGE, JsonStrings.USER_NOT_FOUND)
            		.toString());
		}
		if (tokenExpired) {
			return ResponseEntity.badRequest().body(new JSONObject()
            		.put(JsonStrings.MESSAGE, JsonStrings.TOKEN_EXPIRED)
            		.toString());
		}
		else {
			return null;
		}
	}

}