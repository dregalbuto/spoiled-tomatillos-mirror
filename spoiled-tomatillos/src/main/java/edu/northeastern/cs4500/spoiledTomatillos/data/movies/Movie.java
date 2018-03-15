package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import edu.northeastern.cs4500.spoiledTomatillos.data.reviews.Review;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Class for a movie in Spoiled Tomatillos.
 */
@Data
@Entity(name="movies")
public class Movie {

    @Id
    private String id;
    private String titleType;
    private String title;
    private String releaseDate;
    private String genres;
    private String actors;
    private String description;
    private String runtimeMinuets;
    private String imgURL;
    private String source;
    // list of reviews

    /**
     * A user has many reviews.
     */
    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    private Collection<Review> reviews;

    public Movie() {
        // Empty constructor
    }
}
