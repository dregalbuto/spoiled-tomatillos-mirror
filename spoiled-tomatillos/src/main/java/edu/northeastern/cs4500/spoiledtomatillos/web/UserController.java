package edu.northeastern.cs4500.spoiledtomatillos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.FriendList;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.FriendListRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;

/**
 * Controller for /api/user supports create and login all taking in a json
 * object.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	private final UserService userService;
	private final FriendListRepository friendListRepository;
	
	@Autowired
	UserController(UserService userService, FriendListRepository friendListRepository) {
		this.userService = userService;
		this.friendListRepository = friendListRepository;
	}

	/**
	 * Get the user with given username.
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/username/{username:.+}")
	User getUserByUsername(@PathVariable(required = true) String username) {
		return this.userService.findByUsername(username);
	}

	/**
	 * Get the user with given id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/id/{id:.+}")
	User getUserByID(@PathVariable(required = true) String id) {
		return this.userService.findById(Integer.valueOf(id));
	}
	
	/**
	 * Get the user with given email address.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/email/{email:.+}")
	User getUserByEmail(@PathVariable(required = true) String email) {
		return this.userService.findByEmail(email);
	}

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ResponseEntity<String> loginAccount(@RequestBody String strRequest)
			throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        User user = userService.findByEmail(request.get("email").toString());
        if (user == null){
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message",
							"user with this email doesn't exist").toString());
        }

        String email = request.get("email").toString();
        String password = request.get("password").toString();

        if (!user.isEnabled()) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message",
							"user with this email is disabled").toString());
        }

        if (!user.checkPassword(password)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message",
                            "wrong password").toString());
        }

        try {
            String token = user.getToken(password);
            this.userService.save(user);
            return ResponseEntity.ok().body(
                    new JSONObject().put("message", "logged in").put("token", token).toString());
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message",
                            "illegal access").toString());
        }

    }

	/**
	 * Takes in a JSON object with {first_name, last_name, email, username,
	 * password} and create a new user
	 * @param strRequest JSON with {first_name, last_name, email, username, password}
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ResponseEntity<String> registerUserAccount(@RequestBody String strRequest)
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		User existing = userService.findByEmail(request.get("email").toString());
		if (existing != null){
			return ResponseEntity.badRequest().body(
					new JSONObject().put("message",
							"user with this email already exists").toString());
		}

		String firstname = request.get("first_name").toString();
		String lastname = request.get("last_name").toString();
		String email = request.get("email").toString();
		String username = request.get("username").toString();
		String password = request.get("password").toString();

		User newuser = new User(firstname, lastname, email, username, password);
        userService.save(newuser);
        newuser = this.userService.findByEmail(email);
        FriendList friendList = new FriendList(newuser);
        newuser.setFriends(friendList);
        friendListRepository.save(friendList);
        userService.save(newuser);


		return ResponseEntity.ok().body(
				new JSONObject().put("message", "user created").toString());

	}
}
