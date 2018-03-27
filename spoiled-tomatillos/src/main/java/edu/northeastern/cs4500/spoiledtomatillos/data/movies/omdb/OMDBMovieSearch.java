package edu.northeastern.cs4500.spoiledtomatillos.data.movies.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Yiwen on 2/15/2018.
 */
@Data
public class OMDBMovieSearch {

    @JsonProperty("Search")
    private List<OMDBMovieSearchElement> search;
}
