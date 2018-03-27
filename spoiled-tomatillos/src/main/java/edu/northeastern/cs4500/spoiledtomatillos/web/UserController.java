package edu.northeastern.cs4500.spoiledtomatillos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.FriendList;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.Role;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.FriendListRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

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
		if (userService.findByEmail("tomatillosspoiled@gmail.com") == null) {
			User user = new User("Spoiled", "Tomatillos", "tomatillosspoiled@gmail.com",
							"admin", "admin");
			List<Role> admin = new ArrayList<>();
			admin.add(new Role("ROLE_ADMIN"));
			user.setRoles(admin);
			this.userService.save(user);
		}
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

	@Autowired
	public JavaMailSender emailSender;

	/**
	 * Takes in a request for resetting password and sent to email.
	 * @param strRequest takes in email
	 * @return success or error
	 * @throws JSONException when request is not properly formatted
	 */
	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	public ResponseEntity<String> forget(@RequestBody String strRequest) throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString("email");
		User user = this.userService.findByEmail(email);
		if (user == null) {
			return ResponseEntity.badRequest().body(
							new JSONObject().put("message",
											"cannot find user").toString());
		}
		String pass = user.randomPassword();
		this.userService.save(user);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Reset Password Request");
		message.setText("Your password have been reset, go to /change with password: " + pass
						+ " to change your password");
		this.emailSender.send(message);
		return ResponseEntity.ok().body(
						new JSONObject().put("message", "email sent").toString());
	}

	/**
	 * Change the password with old password to newPassword
	 * @param strRequest request with required info
	 * @return success or failure
	 * @throws JSONException bad request body format
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ResponseEntity<String> change(@RequestBody String strRequest) throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString("email");
		String password = request.get("password").toString();
		String newPassword = request.get("newPassword").toString();
		User user = this.userService.findByEmail(email);
		if (user == null) {
			return ResponseEntity.badRequest().body(
							new JSONObject().put("message",
											"cannot find user").toString());
		}
		if (!user.checkPassword(password)) {
			return ResponseEntity.badRequest().body(
							new JSONObject().put("message",
											"wrong password").toString());
		}
		user.setPassword(newPassword);
		this.userService.save(user);
		return ResponseEntity.ok().body(
						new JSONObject().put("message", "email sent").toString());
	}

	/**
	 * Promote the user if they are admin
	 * @param strRequest
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/promote", method = RequestMethod.POST)
	public ResponseEntity<String> promote(@RequestBody String strRequest) throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString("email");
		String token = request.getString("token");
		String targetEmail = request.getString("targetEmail");
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
							new JSONObject().put("message", "Not a valid login").toString());
		}
		User u = this.userService.findByEmail(email);
		for (Role role : u.getRoles()) {
			if (role.getName().equalsIgnoreCase("ROLE_ADMIN")) {
				User targetUser = this.userService.findByEmail(targetEmail);
				if (targetEmail == null) {
					return ResponseEntity.badRequest().body(
									new JSONObject().put("message", "Not found").toString());
				}
				for (Role targetRole : targetUser.getRoles()) {
					if (targetRole.getName().equalsIgnoreCase("ROLE_ADMIN")) {
						return ResponseEntity.badRequest().body(
										new JSONObject().put("message", "Already admin").toString());
					}
				}
				targetUser.getRoles().add(new Role("ROLE_ADMIN"));
				this.userService.save(targetUser);
				return ResponseEntity.ok().body(
								new JSONObject().put("message", "Ok").toString());
			}
		}
		return ResponseEntity.badRequest().body(
						new JSONObject().put("message", "Not admin").toString());
	}
}
