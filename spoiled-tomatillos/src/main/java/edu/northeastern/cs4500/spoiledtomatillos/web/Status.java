package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;

public abstract class Status {
	User user;
	boolean userExists;
	
	Status(UserService userService, String email) {
		userExists = false;
		user = userService.findByEmail(email);
		userExists = (user == null) ? false : true;
	}
	
	public User getUser() {
		return this.user;
	}
	
	abstract ResponseEntity<String> getResponse() throws JSONException;
}
