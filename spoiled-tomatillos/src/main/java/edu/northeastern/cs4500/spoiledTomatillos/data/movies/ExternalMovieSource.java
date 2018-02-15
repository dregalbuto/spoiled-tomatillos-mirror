package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import java.util.List;

/**
 * Represent an external source to get movies from.
 * @param <T> The type external source queries.
 */
public interface ExternalMovieSource<T> {
    /**
     * Get movie from external source using given identifier.
     * @param id identifier for a movie.
     * @return Movie found with id. null if nothing is found.
     */
    T getMovie(String id);

    /**
     * Search for a movie given given query.
     * @param q The Query to find matching info.
     * @return A list of relevant movies.
     */
    List<T> searchMovie(MovieSearchQuery q);

    /**
     * Convert from Movie to a matching external source as it would be from
     * calling getMovie(id of mov).
     * @param mov Movie to be converting external source
     * @return T movie as represented as T.
     */
    T movieToExternalSource(Movie mov);

    /**
     * Covert from T to a Movie with all the data filled in.
     * @param t External data to be converted to Movie.
     * @return Movie with all the info filled in using data from t.
     */
    Movie externalSourceToMovie(T t);
}
