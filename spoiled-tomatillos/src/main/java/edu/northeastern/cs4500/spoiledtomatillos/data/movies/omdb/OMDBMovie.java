package edu.northeastern.cs4500.spoiledtomatillos.data.movies.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Class Representing Result from OMDB api.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBMovie {
    private String imdbID;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Awards")
    private String awards;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Metascore")
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
    @JsonProperty("Ratings")
    private List<OMDBMovieRatings> ratings;
}
