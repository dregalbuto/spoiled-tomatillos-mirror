package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.groups.Group;
import edu.northeastern.cs4500.spoiledtomatillos.groups.GroupRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.FriendList;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.Role;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.FriendListRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
	private GroupRepository groupRepository;
	
	static final String ROLE = "ROLE_ADMIN";

	@Autowired
	UserController(UserService userService, FriendListRepository friendListRepository) {
		this.userService = userService;
		this.friendListRepository = friendListRepository;
		if (userService.findByEmail("tomatillosspoiled@gmail.com") == null) {
			User user = new User("Spoiled", "Tomatillos", "tomatillosspoiled@gmail.com",
							"admin", "admin");
			List<Role> admin = new ArrayList<>();
			admin.add(new Role(ROLE));
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
	 * Search for a user
	 * @param s search string
	 * @return List of users
	 */
	@CrossOrigin
    @RequestMapping("/search")
    @ResponseBody
    public List<User> search(@RequestParam(required=true) String s) {
		return userService.searchUser(s);
    }
	
	@RequestMapping(value = "/id/{id:.+}/groups")
	List<Group> getGroupsOfUser(@PathVariable(required = true) String id) {
		List<Group> groups = new ArrayList<>();
		for (Group group : this.groupRepository.findAll()) {
			if (String.valueOf(group.getCreator().getId()).equals(id)) {
				groups.add(group);
			}
			for (User user : group.getUsers()) {
				if (String.valueOf(user.getId()).equals(id)) {
					groups.add(group);
				}
			}
		}
		return groups;
	}

	/**
	 * Get the user with given email address.
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/email/{email:.+}")
	User getUserByEmail(@PathVariable(required = true) String email) {
		return this.userService.findByEmail(email);
	}

	@RequestMapping(value = "/email/{email:.+}/groups")
	List<Group> getGroupsOfUserByEmail(@PathVariable(required = true) String email) {
		return this.getGroupsOfUser(String.valueOf(this.userService.findByEmail(email).getId()));
	}

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ResponseEntity<String> loginAccount(@RequestBody String strRequest)
            throws JSONException, IllegalAccessException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        Status userStatus = new TargetStatus(userService, email);
        if (userStatus.getResponse() != null) {
        	return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE,
                           JsonStrings.USER_NOT_FOUND).toString());
        }
        User user = userStatus.getUser();
        String password = request.get(JsonStrings.SECRET).toString();        

        if (!user.checkPassword(password)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE,
                           JsonStrings.BAD_SECRET).toString());
        }
        String token = user.getToken(password);
        this.userService.save(user);
        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.LOGGED_IN)
                        .put(JsonStrings.TOKEN, token).toString());
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
		User existing = userService.findByEmail(request.get(JsonStrings.EMAIL).toString());
		if (existing != null){
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE,
							JsonStrings.USER_EXISTS).toString());
		}

		String firstname = request.get("first_name").toString();
		String lastname = request.get("last_name").toString();
		String email = request.get(JsonStrings.EMAIL).toString();
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
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

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
		String email = request.getString(JsonStrings.EMAIL);
		User user = this.userService.findByEmail(email);
		if (user == null) {
			return ResponseEntity.badRequest().body(
							new JSONObject().put(JsonStrings.MESSAGE,
											JsonStrings.USER_NOT_FOUND).toString());
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
						new JSONObject().put(JsonStrings.MESSAGE,
								JsonStrings.EMAIL_SENT).toString());
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
		String email = request.getString(JsonStrings.EMAIL);
		String password = request.get("password").toString();
		String newPassword = request.get("newPassword").toString();
		User user = this.userService.findByEmail(email);
		if (user == null) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE,
							JsonStrings.USER_NOT_FOUND).toString());
		}
		if (!user.checkPassword(password)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE,
                            JsonStrings.BAD_SECRET).toString());
		}
		user.setPassword(newPassword);
		this.userService.save(user);
		return ResponseEntity.ok().body(
						new JSONObject().put(JsonStrings.MESSAGE,
								JsonStrings.SUCCESS).toString());
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
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE,
							JsonStrings.BAD_SECRET).toString());
		}
		User u = this.userService.findByEmail(email);
		for (Role role : u.getRoles()) {
			if (role.getName().equalsIgnoreCase(ROLE)) {
				User targetUser = this.userService.findByEmail(targetEmail);
				if (targetUser == null) {
					return ResponseEntity.badRequest().body(
							new JSONObject().put(JsonStrings.MESSAGE,
									JsonStrings.TARGET_USER_NOT_FOUND).toString());
				}
				for (Role targetRole : targetUser.getRoles()) {
					if (targetRole.getName().equalsIgnoreCase(ROLE)) {
						return ResponseEntity.badRequest().body(
										new JSONObject().put(JsonStrings.MESSAGE,
												JsonStrings.ADMIN_EXISTS).toString());
					}
				}
				targetUser.getRoles().add(new Role(ROLE));
				this.userService.save(targetUser);
				return ResponseEntity.ok().body(
						new JSONObject().put(JsonStrings.MESSAGE,
								JsonStrings.SUCCESS).toString());
			}
		}
		return ResponseEntity.badRequest().body(
						new JSONObject().put(JsonStrings.MESSAGE,
								JsonStrings.NO_PERMISSION).toString());
	}
}
