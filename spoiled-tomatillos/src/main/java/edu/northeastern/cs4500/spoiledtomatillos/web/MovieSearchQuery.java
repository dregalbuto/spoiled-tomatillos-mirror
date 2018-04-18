package edu.northeastern.cs4500.spoiledtomatillos.web;

import lombok.Data;
import lombok.NonNull;

/**
 * Query information to find match for movies with info from this query.
 */
@Data
public class MovieSearchQuery {

    private @NonNull
    String title;
}
