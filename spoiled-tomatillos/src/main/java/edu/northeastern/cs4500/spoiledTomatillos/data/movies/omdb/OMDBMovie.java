package edu.northeastern.cs4500.spoiledTomatillos.data.movies.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Class Representing Result from OMDB api.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBMovie {
    private String imdbID;
    private String Title;
    private String Year;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Type;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private List<OMDBMovieRatings> Ratings;
}
