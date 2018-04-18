package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.northeastern.cs4500.spoiledtomatillos.groups.Group;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;

/**
 * Class for a review written by a user
 */

@Data
@Entity(name = "reviews")
@JsonSerialize(using = ReviewSeralizer.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;
    private int rating;

    /**
     * A movie has many reviews.
     */
    @JsonBackReference
    @ManyToOne
    private Movie movie;

    /**
     * A user has many reviews.
     */
    @JsonBackReference
    @ManyToOne
    private User user;

    @JsonBackReference
    @ManyToOne
    private Group group;

    /**
     * Default constructor for a Review.
     */
    public Review() {
        // Default Constructor
    }

    /**
     * Constructor that have all the fields.
     * @param text Text of the review.
     * @param rating Rating 0 to 5 for this movie.
     * @param movie Movie this rating is for.
     * @param user Source of this review.
     */
    public Review(String text, int rating, Movie movie, User user) {
        this.text = text;
        this.rating = rating;
        this.movie = movie;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int identification) {
        this.id = identification;
    }

    public String getText() {
        return text;
    }

    public void setText(String reviewText) {
        this.text = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int reviewRating) {
        this.rating = reviewRating;
    }

}

/**
 * Serializes Review to JSON.
 */
class ReviewSeralizer extends StdSerializer<Review> {

    /**
     * Default constructor.
     */
    public ReviewSeralizer() {
        this(null);
    }

    /**
     * Pass to super constructor.
     * @param t type of Review class.
     */
    protected ReviewSeralizer(Class<Review> t) {
        super(t);
    }

    /**
     * Converts given Review to JSON using the generator.
     * @param review Reivew to convert to JSON.
     * @param jsonGenerator Generator to create the JSON.
     * @param serializerProvider Not used.
     * @throws IOException when JSON generator throws one.
     */
    @Override
    public void serialize(Review review, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", review.getId());
        jsonGenerator.writeStringField("text", review.getText());
        jsonGenerator.writeNumberField("rating", review.getRating());
        if (review.getGroup() != null) {
            jsonGenerator.writeStringField("group", String.valueOf(review.getGroup().getId()));
        }
        jsonGenerator.writeObjectFieldStart("movie");
        jsonGenerator.writeStringField("id", review.getMovie().getId());
        jsonGenerator.writeStringField("title", review.getMovie().getTitle());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeBooleanField("critic", review.getUser() == null);
        if (review.getUser() != null) {
            jsonGenerator.writeNumberField("userId", review.getUser().getId());
        }
        jsonGenerator.writeEndObject();
    }
}
