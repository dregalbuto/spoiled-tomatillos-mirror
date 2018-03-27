package edu.northeastern.cs4500.spoiledtomatillos.data.movies.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Yiwen on 2/16/2018.
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
