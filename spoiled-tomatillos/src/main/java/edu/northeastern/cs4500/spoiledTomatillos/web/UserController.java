package edu.northeastern.cs4500.spoiledTomatillos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;

/**
 * This controller serves User-specific API endpoints for the web app
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	@Autowired
	UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/foo")
	String getFoo() {
		return "Foo";
	}
	
	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
	User getUserByUsername(@PathVariable String username) {
		return this.userService.findByUsername(username);
	}
	
	
	
}
