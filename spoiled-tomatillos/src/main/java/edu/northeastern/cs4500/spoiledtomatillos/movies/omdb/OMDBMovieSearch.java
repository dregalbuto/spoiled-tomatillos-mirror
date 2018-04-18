package edu.northeastern.cs4500.spoiledtomatillos.movies.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Represent a search result from OMDB.
 */
@Data
public class OMDBMovieSearch {

    @JsonProperty("Search")
    private List<OMDBMovieSearchElement> search;
}
