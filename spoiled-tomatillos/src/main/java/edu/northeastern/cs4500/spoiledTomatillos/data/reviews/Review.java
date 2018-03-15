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
    private Collection<Movie> movie;

    /**
     * A user has many reviews.
     */
    @ManyToOne
    @JoinColumn(name="users")
    private Collection<User> user;

    public Review() {

    }


}
