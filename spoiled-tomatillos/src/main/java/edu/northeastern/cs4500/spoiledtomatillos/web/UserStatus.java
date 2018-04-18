package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

/**
 * Helper to user status.
 */
public class UserStatus extends Status {

    boolean tokenExpired;

    /**
     * Generate based on user email and token.
     * @param userService Where the users are stored.
     * @param email email of the user.
     * @param token token of the user.
     */
    UserStatus(UserService userService, String email, String token) {
        super(userService, email);
        tokenExpired = true;
        tokenExpired = (user != null) && (!user.validToken(token));
    }

    /**
     * Get the response associated for this user login.
     * @return JSON response.
     * @throws JSONException if json fail to generate.
     */
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
        } else {
            return null;
        }
    }

}