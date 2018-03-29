package edu.northeastern.cs4500.spoiledtomatillos.movies;

import java.util.List;

import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;

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

    /**
     * Import the critic review for a certain id.
     * @param movie the movie to import.
     * @return All the reviews we have for a given movie.
     */
    List<Review> importCriticReview(Movie movie);
}
