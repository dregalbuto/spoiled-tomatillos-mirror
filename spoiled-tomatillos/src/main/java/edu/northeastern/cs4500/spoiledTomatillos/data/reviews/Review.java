package edu.northeastern.cs4500.spoiledTomatillos.data.reviews;

import edu.northeastern.cs4500.spoiledTomatillos.data.movies.Movie;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;


/**
 * Class for a review written by a user
 */

@Data
@Entity(name="reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;
    private int rating;

    /**
     * A movie has many reviews.
     */
    @ManyToOne
    @JoinColumn(name="movies")
    private Movie movie;

    /**
     * A user has many reviews.
     */
    @ManyToOne
    @JoinColumn(name="users")
    private User user;

    public Review() {

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
