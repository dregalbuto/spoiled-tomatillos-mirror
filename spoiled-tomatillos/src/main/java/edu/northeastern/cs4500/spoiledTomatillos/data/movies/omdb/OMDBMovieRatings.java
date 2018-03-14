package edu.northeastern.cs4500.spoiledTomatillos.data.movies.omdb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represent ratings returned from OMDB api.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBMovieRatings {
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;
}
