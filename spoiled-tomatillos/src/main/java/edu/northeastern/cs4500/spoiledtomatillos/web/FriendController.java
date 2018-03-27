package edu.northeastern.cs4500.spoiledtomatillos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;


@RestController
@RequestMapping(value = "/api/friend")
public class FriendController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendFriendRequest(@RequestBody String strRequest) 
    		throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.INVALID_LOGIN).toString());
        }
        User u = this.userService.findByEmail(email);
        User target = this.userService.findByEmail(targetEmail);
        if (target == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.TARGET_USER_NOT_FOUND).toString());
        }
        boolean success = target.getFriends().addRequest(u);
        if (!success) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.ERROR).toString());
        }
        this.userService.save(target);
        this.userService.save(u);
        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public ResponseEntity<String> acceptFriendRequest(@RequestBody String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.INVALID_LOGIN).toString());
        }
        User u = this.userService.findByEmail(email);
        User targetUser = this.userService.findByEmail(targetEmail);
        boolean success = u.getFriends().acceptRequest(targetUser);
        if (!success) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
        }
        this.userService.save(u);
        this.userService.save(targetUser);
        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity<String> rejectFriendRequest(@RequestBody String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        String targetEmail = request.getString(JsonStrings.TARGET_EMAIL);
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.INVALID_LOGIN).toString());
        }
        User u = this.userService.findByEmail(email);
        boolean success = u.getFriends().rejectRequest(this.userService.findByEmail(targetEmail));
        if (!success) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
        }
        this.userService.save(u);
        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

    }

    @RequestMapping(value = "/unfriend", method = RequestMethod.POST)
    public ResponseEntity<String> unfriend(@RequestBody String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        String targetEmail = request.getString("targetEmail");
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.INVALID_LOGIN).toString());
        }
        User u = this.userService.findByEmail(email);
        User targetUser = this.userService.findByEmail(targetEmail);
        boolean success = u.getFriends().removeFriend(targetUser);
        if (!success) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
        }
        this.userService.save(u);
        this.userService.save(targetUser);
        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS).toString());

    }

    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    public ResponseEntity<String> list(@RequestBody String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.INVALID_LOGIN).toString());
        }
        try {
            return ResponseEntity.ok().body(
                    new ObjectMapper().writeValueAsString(
                            this.userService.findByEmail(email).getFriends().getFriends()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
        }
    }

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<String> request(@RequestBody String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String email = request.getString(JsonStrings.EMAIL);
        String token = request.getString(JsonStrings.TOKEN);
        if (!User.validLogin(email, token, this.userService)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.INVALID_LOGIN).toString());
        }
        try {
            return ResponseEntity.ok().body(
                    new ObjectMapper().writeValueAsString(
                            this.userService.findByEmail(email).getFriends().getRequests()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.ERROR).toString());
        }
    }
}
