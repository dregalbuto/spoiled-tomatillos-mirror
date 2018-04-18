package edu.northeastern.cs4500.spoiledtomatillos.recommendations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;

/**
 * Represent a recommendation for a Movie.
 */
@Data
@Entity(name = "recommendations")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonPropertyOrder({"recommendationId", "message"})
@ResponseBody
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(JsonStrings.REC_ID)
    private int id;

    // The user who made the recommendation
    // Default is null because rec. might not have a source
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@JsonBackReference
    private User recommendationGiver = null;

    // Message from the recommendationGiver
    // Default is null because rec. might not have a message
    private String message = null;

    // The movie being recommended
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    //@JsonBackReference
    private Movie movie;

    /**
     * Default constructor.
     */
    public Recommendation() {
        // empty constructor
    }

    /**
     * Constructor that populates all the fields.
     * @param source Source of this recommendation.
     * @param movie Movie that is being recommended.
     * @param message Message for this to be recommended.
     */
    public Recommendation(User source, Movie movie, String message) {
        this.recommendationGiver = source;
        this.movie = movie;
        this.message = message;
    }

    /**
     * Constructor that only populate the movie field from system generated Recommendation.
     * @param movie
     */
    public Recommendation(Movie movie) {
        this.movie = movie;
    }


}
