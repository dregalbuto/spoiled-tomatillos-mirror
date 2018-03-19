package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class FriendController {

    @Autowired
    private UserController userController;

    @RequestMapping(value = "/friend/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendFrendRequest(@RequestBody String strRequest) {

    }

    @RequestMapping(value = "/friend/accept", method = RequestMethod.POST)
    public ResponseEntity<String> acceptFrendRequest(@RequestBody String strRequest) {

    }

    @RequestMapping(value = "/friend/reject", method = RequestMethod.POST)
    public ResponseEntity<String> rejectFrendRequest(@RequestBody String strRequest) {

    }

    @RequestMapping(value = "/friend/unfriend", method = RequestMethod.POST)
    public ResponseEntity<String> unfriend(@RequestBody String strRequest) {

    }

    @RequestMapping(value = "/friend/friends", method = RequestMethod.POST)
    public ResponseEntity<String> list(@RequestBody String strRequest) {

    }

    @RequestMapping(value = "/friend/requests", method = RequestMethod.POST)
    public ResponseEntity<String> request(@RequestBody String strRequest) {

    }
}
