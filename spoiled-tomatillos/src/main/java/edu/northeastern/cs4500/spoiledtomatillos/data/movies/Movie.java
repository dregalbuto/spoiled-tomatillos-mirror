package edu.northeastern.cs4500.spoiledtomatillos.data.movies;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    public Movie() {
        // Empty constructor
    }
}
