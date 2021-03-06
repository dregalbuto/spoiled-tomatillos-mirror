package edu.northeastern.cs4500.spoiledtomatillos.movies.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represent movie information from OMDB search quarry.
 */
@Data
public class OMDBMovieSearchElement {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
}
