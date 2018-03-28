package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
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
        String token = request.getString(JsonStrings.TOKEN);
        String rating = request.getString(JsonStrings.RATING);
        String email = request.getString(JsonStrings.EMAIL);
        String text = request.getString(JsonStrings.TEXT);
        String movieId = request.getString(JsonStrings.MOVIE_ID);
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.USER_NOT_FOUND).toString());
        }
        if (!user.validToken(token)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.TOKEN_EXPIRED).toString());
        }
        Movie movie = movieCachedRepository.getMovie(movieId);
        if (movie == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.MOVIE_NOT_FOUND).toString());
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
        String token = request.getString(JsonStrings.TOKEN);
        String email = request.getString(JsonStrings.EMAIL);
        String postId = request.getString(JsonStrings.POST_ID);
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.USER_NOT_FOUND).toString());
        }
        if (!user.validToken(token)) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.TOKEN_EXPIRED).toString());
        }
        Review review = this.reviewRepository.findOne(new Integer(postId));
        if (review == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.REVIEW_NOT_FOUND).toString());
        }
        if (review.getUser().getId() != user.getId()) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.NO_PERMISSION).toString());
        }

        this.reviewRepository.delete(review);

        return ResponseEntity.ok().body(
                new JSONObject().put(JsonStrings.MESSAGE
                		, JsonStrings.SUCCESS).toString());
    }

    @RequestMapping("/get")
    public ResponseEntity<String> getReview(@RequestBody(required = true)String strRequest)
            throws JSONException, JsonProcessingException {
        JSONObject request = new JSONObject(strRequest);
        String postId = request.getString(JsonStrings.POST_ID);
        Review review = this.reviewRepository.findOne(new Integer(postId));
        if (review == null) {
            return ResponseEntity.badRequest().body(
                    new JSONObject().put(JsonStrings.MESSAGE
                    		, JsonStrings.REVIEW_NOT_FOUND).toString());
        }
        return ResponseEntity.ok().body(
                new ObjectMapper().writeValueAsString(review));
    }
}
