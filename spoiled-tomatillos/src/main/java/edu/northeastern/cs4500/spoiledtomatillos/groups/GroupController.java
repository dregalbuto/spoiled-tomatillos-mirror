package edu.northeastern.cs4500.spoiledtomatillos.groups;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.movies.MovieCachedRepository;
import edu.northeastern.cs4500.spoiledtomatillos.movies.MovieRepository;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.ReviewRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;
import net.minidev.json.reader.JsonWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing groups
 */
@Controller
@RequestMapping("/api/groups")
public class GroupController {
  @Autowired
  GroupRepository groupRepository;
  @Autowired
  MovieCachedRepository movieCachedRepository;
  @Autowired
  UserServiceImpl userService;
  @Autowired
  ReviewRepository reviewRepository;

  @RequestMapping("/create")
  public ResponseEntity<String> create(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupName = request.getString("groupName");
    String privacy = request.getString("blacklist");
    String movieId = request.getString("movieId");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Movie movie = this.movieCachedRepository.getMovie(movieId);
    if (movie == null) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "No movie found with id").toString());
    }

    Group group = new Group(u, movie, groupName, "true".equalsIgnoreCase(privacy));
    u.getGroups().add(group);
    group = this.groupRepository.save(group);
    this.userService.save(u);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

  @RequestMapping("/delete")
  public ResponseEntity<String> delete(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));
    if (group.getCreator().getId() == u.getId()) {
      //TODO replace with permission
      u.getGroups().remove(group);
      this.groupRepository.delete(group);
      return ResponseEntity.ok().body(
              new JSONObject().put("message", "Group deleted").toString());
    }
    return ResponseEntity.badRequest().body(
            new JSONObject().put("message", "No permission to delete").toString());
  }

  @RequestMapping("/join")
  public ResponseEntity<String> join(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));
    if (!group.addUser(u)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Cannot join group").toString());
    }
    this.groupRepository.save(group);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

  @RequestMapping("/leave")
  public ResponseEntity<String> leave(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));
    if (!group.removeUser(u)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Cannot join group").toString());
    }
    this.groupRepository.save(group);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

  @RequestMapping("/addtolist")
  public ResponseEntity<String> addtoList(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    String userEmail = request.getString("userEmail");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);
    User otherUser = this.userService.findByEmail(userEmail);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));

    if (group.getCreator().getId() != u.getId()) {
      //TODO replace with perm
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "No not have permission").toString());
    }
    if (!group.getIdList().add(otherUser.getId())) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Cannot join group").toString());
    }
    this.groupRepository.save(group);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

  @RequestMapping("/removefromlist")
  public ResponseEntity<String> removefromList(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    String userEmail = request.getString("userEmail");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);
    User otherUser = this.userService.findByEmail(userEmail);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));

    if (group.getCreator().getId() != u.getId()) {
      //TODO replace with perm
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "No not have permission").toString());
    }
    if (!group.getIdList().remove(otherUser.getId())) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Cannot join group").toString());
    }
    this.groupRepository.save(group);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

  @RequestMapping("/post")
  public ResponseEntity<String> post(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    String rating = request.getString("rating");
    String text = request.getString("text");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));

    if (!group.contains(u)) {
      //TODO replace with perm
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "No not have permission").toString());
    }
    Review review = new Review(text, Integer.valueOf(rating), group.getTopic(), u);
    group.getReviews().add(review);
    this.groupRepository.save(group);
    return ResponseEntity.ok().body(
            new JSONObject().put("message", "Success")
                    .put("groupId", group.getId()).toString());
  }

//  @RequestMapping("/remove")
//  public ResponseEntity<String> remove(@RequestBody(required = true)String strRequest) throws JSONException {
//    JSONObject request = new JSONObject(strRequest);
//    String email = request.getString("email");
//    String token = request.getString("token");
//    String groupId = request.getString("groupId");
//    String reviewId = request.getString("reviewId");
//    if (!User.validLogin(email, token, this.userService)) {
//      return ResponseEntity.badRequest().body(
//              new JSONObject().put("message", "Not a valid login").toString());
//    }
//    User u = this.userService.findByEmail(email);
//
//    Group group = groupRepository.findOne(Integer.valueOf(groupId));
//
//    if (!group.contains(u)) {
//      //TODO replace with perm
//      return ResponseEntity.badRequest().body(
//              new JSONObject().put("message", "No not have permission").toString());
//    }
//    Review review = this.reviewRepository.findOne(Integer.valueOf(reviewId));
//    if (review == null) {
//      return ResponseEntity.badRequest().body(
//              new JSONObject().put("message", "Review not found").toString());
//    }
//    if (review.get) {
//
//    }
//    group.getReviews().add(review);
//    this.groupRepository.save(group);
//    return ResponseEntity.ok().body(
//            new JSONObject().put("message", "Success")
//                    .put("groupId", group.getId()).toString());
//  }

  @RequestMapping("/get")
  public ResponseEntity<String> get(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String email = request.getString("email");
    String token = request.getString("token");
    String groupId = request.getString("groupId");
    if (!User.validLogin(email, token, this.userService)) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Not a valid login").toString());
    }
    User u = this.userService.findByEmail(email);

    Group group = groupRepository.findOne(Integer.valueOf(groupId));

    if (!group.contains(u)) {
      //TODO replace with perm
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "No not have permission").toString());
    }
    this.groupRepository.save(group);
    try {
      return ResponseEntity.ok().body(
              new ObjectMapper().writeValueAsString(group));
    } catch (JsonProcessingException e) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Error processing request").toString());
    }
  }

  @RequestMapping("/search")
  public ResponseEntity<String> search(@RequestBody(required = true)String strRequest) throws JSONException {
    JSONObject request = new JSONObject(strRequest);
    String querry = request.getString("s");
    List<Integer> groups = new ArrayList<>();
    for (Group g : this.groupRepository.findAll()) {
      if (g.getName().contains(querry)) {
        groups.add(g.getId());
      }
    }
    try {
      return ResponseEntity.ok().body(
              new ObjectMapper().writeValueAsString(groups));
    } catch (JsonProcessingException e) {
      return ResponseEntity.badRequest().body(
              new JSONObject().put("message", "Error processing request").toString());
    }
  }
}
