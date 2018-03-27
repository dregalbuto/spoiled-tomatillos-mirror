package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import com.fasterxml.jackson.annotation.JsonBackReference;

import edu.northeastern.cs4500.spoiledtomatillos.groups.Group;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;
import javax.persistence.*;


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
