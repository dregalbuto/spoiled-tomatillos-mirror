package edu.northeastern.cs4500.spoiledTomatillos.data.movies.omdb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Represent ratings returned from OMDB api.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBMovieRatings {
    private String Source;
    private String Value;
}
