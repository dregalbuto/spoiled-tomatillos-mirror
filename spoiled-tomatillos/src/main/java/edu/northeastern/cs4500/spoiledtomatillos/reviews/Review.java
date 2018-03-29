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
@Entity(name="reviews")
@JsonSerialize(using = ReviewSeralizer .class)
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
    @ManyToOne//(cascade = CascadeType.)
    //@JoinColumn(name="movies")
    private Movie movie;

    /**
     * A user has many reviews.
     */
    @JsonBackReference
    @ManyToOne//(cascade = CascadeType.ALL)
    //@JoinColumn(name="users")
    private User user;

    @JsonBackReference
    @ManyToOne//(cascade = Casca)
    private Group group;

    public Review() {

    }

    public Review(String text, int rating, Movie movie, User user) {
        this.text = text;
        this.rating = rating;
        this.movie = movie;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public void setId(int identification) {
        this.id = identification;
    }

    public void setText(String reviewText) {
        this.text = reviewText;
    }

    public void setRating(int reviewRating) {
        this.rating = reviewRating;
    }

}

class ReviewSeralizer extends StdSerializer<Review> {

    public ReviewSeralizer() {
        this(null);
    }

    protected ReviewSeralizer(Class<Review> t) {
        super(t);
    }

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
        jsonGenerator.writeEndObject();
    }
}