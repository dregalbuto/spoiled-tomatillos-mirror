package edu.northeastern.cs4500.spoiledtomatillos.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing groups
 */
@Controller
@RequestMapping("/api/groups")
public class GroupController {
  @Autowired
  GroupRepository groupRepository;

  @RequestMapping("/create")
  public ResponseEntity<String> create(@RequestBody(required = true)String strRequest) {

  }

  @RequestMapping("/delete")
  public ResponseEntity<String> delete(@RequestBody(required = true)String strRequest) {

  }

  @RequestMapping("/join")
  public ResponseEntity<String> join(@RequestBody(required = true)String strRequest) {

  }

  @RequestMapping("/leave")
  public ResponseEntity<String> leave(@RequestBody(required = true)String strRequest) {

  }

  @RequestMapping("/appendlist")
  public ResponseEntity<String> appendList(@RequestBody(required = true)String strRequest) {

  }

  @RequestMapping("/post")
  public ResponseEntity<String> post(@RequestBody(required = true)String strRequest) {

  }
}
