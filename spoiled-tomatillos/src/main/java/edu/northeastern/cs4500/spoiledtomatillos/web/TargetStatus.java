package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

/**
 * Common return when controller is acting on target.
 */
public class TargetStatus extends Status {

    /**
     * Generate target based on email.
     * @param userService service that have all the users.
     * @param email email of the user to target.
     */
    TargetStatus(UserService userService, String email) {
        super(userService, email);
    }

    /**
     * Response for acting on target.
     * @return JSON response.
     * @throws JSONException if json fail to generate.
     */
    ResponseEntity<String> getResponse() throws JSONException {
        if (!userExists) {
            return ResponseEntity.badRequest().body(new JSONObject()
                    .put(JsonStrings.MESSAGE, JsonStrings.TARGET_USER_NOT_FOUND)
                    .toString());
        } else {
            return null;
        }
    }
}
