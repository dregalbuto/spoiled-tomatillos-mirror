package edu.northeastern.cs4500.spoiledtomatillos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.groups.Group;
import edu.northeastern.cs4500.spoiledtomatillos.groups.GroupRepository;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.movies.MovieCachedRepository;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.ReviewRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;
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

	private class Helper {
		
		ResponseEntity<String> response;
		User u;
		User otherUser;
		Group group;
		
		Helper(String strRequest) throws JSONException {
			response = null;
			otherUser = null;
			JSONObject request = new JSONObject(strRequest);
			String email = request.getString(JsonStrings.EMAIL);
			String token = request.getString(JsonStrings.TOKEN);
			String groupId = request.getString(JsonStrings.GROUP_ID);
			if (!User.validLogin(email, token, userService)) {
				response = ResponseEntity.badRequest().body(
						new JSONObject().put(JsonStrings.MESSAGE
								, JsonStrings.INVALID_LOGIN).toString());
			}
			u = userService.findByEmail(email);
			group = groupRepository.findOne(Integer.valueOf(groupId));
			if (request.has(JsonStrings.TARGET_EMAIL)) {
				String userEmail = request.getString(JsonStrings.TARGET_EMAIL);
				otherUser = userService.findByEmail(userEmail);
			}
		}
	}
	
	@RequestMapping("/create")
	public ResponseEntity<String> create(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String groupName = request.getString(JsonStrings.GROUP_NAME);
		String privacy = request.getString(JsonStrings.BLACKLIST);
		String movieId = request.getString(JsonStrings.MOVIE_ID);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.INVALID_LOGIN).toString());
		}
		User u = this.userService.findByEmail(email);

		Movie movie = this.movieCachedRepository.getMovie(movieId);
		if (movie == null) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.MOVIE_NOT_FOUND).toString());
		}

		Group group = new Group(u, movie, groupName, "true".equalsIgnoreCase(privacy));
		u.getGroups().add(group);
		group = this.groupRepository.save(group);
		this.userService.save(u);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, group.getId()).toString());
	}

	@RequestMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String groupId = request.getString(JsonStrings.GROUP_ID);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.INVALID_LOGIN).toString());
		}
		User u = this.userService.findByEmail(email);

		Group group = groupRepository.findOne(Integer.valueOf(groupId));
		if (group.getCreator().getId() == u.getId()) {
			u.getGroups().remove(group);
			this.groupRepository.delete(group);
			return ResponseEntity.ok().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.SUCCESS).toString());
		}
		return ResponseEntity.badRequest().body(
				new JSONObject().put(JsonStrings.MESSAGE
						, JsonStrings.NO_PERMISSION).toString());
	}

	@RequestMapping("/join")
	public ResponseEntity<String> join(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String groupId = request.getString(JsonStrings.GROUP_ID);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.INVALID_LOGIN).toString());
		}
		User u = this.userService.findByEmail(email);

		Group group = groupRepository.findOne(Integer.valueOf(groupId));
		if (!group.addUser(u)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.CANNOT_JOIN).toString());
		}
		this.groupRepository.save(group);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, group.getId()).toString());
	}

	@RequestMapping("/leave")
	public ResponseEntity<String> leave(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.response != null) { return h.response; }
		if (!h.group.removeUser(h.u)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.CANNOT_LEAVE).toString());
		}
		this.groupRepository.save(h.group);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, h.group.getId()).toString());
	}

	@RequestMapping("/addtolist")
	public ResponseEntity<String> addtoList(@RequestBody(required = true)String strRequest)
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.response != null) { return h.response; }
		if (h.group.getCreator().getId() != h.u.getId()) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.NO_PERMISSION).toString());
		}
		if (h.group.getIdList().contains(h.otherUser.getId()) ||
				!h.group.getIdList().add(h.otherUser.getId())) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.CANNOT_JOIN).toString());
		}
		this.groupRepository.save(h.group);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, h.group.getId()).toString());
	}

	@RequestMapping("/removefromlist")
	public ResponseEntity<String> removefromList(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		Helper h = new Helper(strRequest);
		if (h.response != null) { return h.response; }

		if (h.group.getCreator().getId() != h.u.getId()) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.NO_PERMISSION).toString());
		}
		if (!h.group.getIdList().remove(h.otherUser.getId())) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.CANNOT_JOIN).toString());
		}
		this.groupRepository.save(h.group);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, h.group.getId()).toString());
	}

	@RequestMapping("/post")
	public ResponseEntity<String> post(@RequestBody(required = true)String strRequest) 
			throws JSONException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String groupId = request.getString(JsonStrings.GROUP_ID);
		String rating = request.getString(JsonStrings.RATING);
		String text = request.getString(JsonStrings.TEXT);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.INVALID_LOGIN).toString());
		}
		User u = this.userService.findByEmail(email);

		Group group = groupRepository.findOne(Integer.valueOf(groupId));

		if (!group.contains(u)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.NO_PERMISSION).toString());
		}
		Review review = new Review(text, Integer.valueOf(rating), group.getTopic(), u);
		group.getReviews().add(review);
		this.groupRepository.save(group);
		return ResponseEntity.ok().body(
				new JSONObject().put(JsonStrings.MESSAGE, JsonStrings.SUCCESS)
				.put(JsonStrings.GROUP_ID, group.getId()).toString());
	}

	@RequestMapping("/get")
	public ResponseEntity<String> get(@RequestBody(required = true)String strRequest)
            throws JSONException, JsonProcessingException {
		JSONObject request = new JSONObject(strRequest);
		String email = request.getString(JsonStrings.EMAIL);
		String token = request.getString(JsonStrings.TOKEN);
		String groupId = request.getString(JsonStrings.GROUP_ID);
		if (!User.validLogin(email, token, this.userService)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.INVALID_LOGIN).toString());
		}
		User u = this.userService.findByEmail(email);

		Group group = groupRepository.findOne(Integer.valueOf(groupId));

		if (!group.contains(u)) {
			return ResponseEntity.badRequest().body(
					new JSONObject().put(JsonStrings.MESSAGE
							, JsonStrings.NO_PERMISSION).toString());
		}
		this.groupRepository.save(group);
        return ResponseEntity.ok().body(
                new ObjectMapper().writeValueAsString(group));
	}

	@RequestMapping("/search")
	public ResponseEntity<String> search(@RequestBody(required = true)String strRequest)
			throws JSONException, JsonProcessingException {
		JSONObject request = new JSONObject(strRequest);
		String query = request.getString("s");
		List<Integer> groups = new ArrayList<>();
		for (Group g : this.groupRepository.findAll()) {
			if (g.getName().contains(query)) {
				groups.add(g.getId());
			}
		}
		return ResponseEntity.ok().body(
				new ObjectMapper().writeValueAsString(groups));
	}
}
