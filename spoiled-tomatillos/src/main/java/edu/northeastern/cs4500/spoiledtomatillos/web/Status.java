package edu.northeastern.cs4500.spoiledtomatillos.web;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;

/**
 * Common status to return from controllers.
 */
public abstract class Status {
    protected User user;
    protected boolean userExists;

    /**
     * Constructs status from service and email.
     * @param userService Service that have the users.
     * @param email email of the user this is for.
     */
    Status(UserService userService, String email) {
        userExists = false;
        user = userService.findByEmail(email);
        userExists = (user == null) ? false : true;
    }

    /**
     * Get the user associated with the status.
     * @return user for this status.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Get the response from this status.
     * @return JSON response.
     * @throws JSONException if json fail to generate.
     */
    abstract ResponseEntity<String> getResponse() throws JSONException;
}
