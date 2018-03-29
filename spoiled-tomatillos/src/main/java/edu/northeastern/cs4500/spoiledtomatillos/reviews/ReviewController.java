package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.movies.MovieCachedRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that performs actions on reviews.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MovieCachedRepository movieCachedRepository;

    @RequestMapping("/post")
    public ResponseEntity<String> postReview(@RequestBody(required = true)String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String token = request.getString("token");
        String rating = request.getString("rating");
        String email = request.getString("email");
        String text = request.getString("text");
        String movieId = request.getString("movieId");
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "user not found").toString());
        }
        if (!user.validToken(token)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "not logged in").toString());
        }
        Movie movie = movieCachedRepository.getMovie(movieId);
        if (movie == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "movie not found").toString());
        }

        Review review = new Review(text, new Integer(rating), movie, user);
        this.reviewRepository.saveAndFlush(review);

        return ResponseEntity.ok().body(
                new JSONObject().put("message", "ok")
                        .put("reviewId", review.getId()).toString());
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> deleteReview(@RequestBody(required = true)String strRequest)
            throws JSONException {
        JSONObject request = new JSONObject(strRequest);
        String token = request.getString("token");
        String email = request.getString("email");
        String postId = request.getString("postId");
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "user not found").toString());
        }
        if (!user.validToken(token)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "not logged in").toString());
        }
        Review review = this.reviewRepository.findOne(new Integer(postId));
        if (review == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "review doesn't exist").toString());
        }
        if (review.getUser().getId() != user.getId()) {
            //TODO use role object to determine permission
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "wrong user").toString());
        }

        this.reviewRepository.delete(review);
        this.reviewRepository.flush();
        return ResponseEntity.ok().body(
                new JSONObject().put("message", "ok").toString());
    }

    @RequestMapping("/get")
    public ResponseEntity<String> getReview(@RequestBody(required = true)String strRequest)
            throws JSONException, JsonProcessingException {
        JSONObject request = new JSONObject(strRequest);
        String postId = request.getString("postId");
        Review review = this.reviewRepository.findOne(new Integer(postId));
        if (review == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put("message", "review doesn't exist").toString());
        }
        return ResponseEntity.ok().body(
                new ObjectMapper().writeValueAsString(review));
    }
}
