package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import lombok.Data;

/**
 * Query information to find match for movies with info from this query.
 */
@Data
public class MovieSearchQuery {
    private String title;
}
