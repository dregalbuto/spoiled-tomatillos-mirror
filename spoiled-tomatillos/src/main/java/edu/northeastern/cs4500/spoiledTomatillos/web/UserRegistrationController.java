package edu.northeastern.cs4500.spoiledTomatillos.web;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;
import edu.northeastern.cs4500.spoiledTomatillos.web.dto.UserRegistrationDto;

/**
 * Controller that performs actions on user signup.
 */
@RestController
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @RequestMapping(path="api/signup", method=RequestMethod.POST)
    public ResponseEntity<JSONObject> registerUserAccount(@RequestBody JSONObject request) throws JSONException{

        User existing = userService.findByEmail(request.get("email").toString());
        if (existing != null){
            //result.rejectValue("email", null, "There is already an account registered with that email");
            return ResponseEntity.badRequest().body(
            		new JSONObject().put("message", "user with this email already exists"));
        }

        String firstname = request.get("first_name").toString();
        String lastname = request.get("last_name").toString();
        String email = request.get("email").toString();
        String username = request.get("username").toString();
        String password = request.get("password").toString();
        
        	User newuser = new User(firstname, lastname, email, username, password);
        	userService.save(newuser);
        	
        	return ResponseEntity.ok().body(
        			new JSONObject().put("message", "user created"));	
 
    }
}
