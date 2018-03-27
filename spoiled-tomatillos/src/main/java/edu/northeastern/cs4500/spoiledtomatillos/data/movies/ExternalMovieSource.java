package edu.northeastern.cs4500.spoiledtomatillos.data.movies;

import java.util.List;

/**
 * Represent an external source to get movies from.
 */
public interface ExternalMovieSource {
    /**
     * Get movie from external source using given identifier.
     * @param id identifier for a movie.
     * @return Movie found with id. null if nothing is found.
     */
    Movie getMovie(String id);

    /**
     * Search for a movie given given query.
     * @param q The Query to find matching info.
     * @return A list of relevant movie ids.
     */
    List<String> searchMovie(MovieSearchQuery q);
}
