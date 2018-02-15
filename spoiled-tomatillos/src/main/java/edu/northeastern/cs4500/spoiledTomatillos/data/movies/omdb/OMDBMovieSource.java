package edu.northeastern.cs4500.spoiledTomatillos.data.movies.omdb;

import edu.northeastern.cs4500.spoiledTomatillos.data.movies.ExternalMovieSource;
import edu.northeastern.cs4500.spoiledTomatillos.data.movies.Movie;
import edu.northeastern.cs4500.spoiledTomatillos.data.movies.MovieSearchQuery;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OMDBMovieSource implements ExternalMovieSource<OMDBMovie> {
    private final String APIKEY = "3ef62c6c";

    /**
     * Get movie from external source using given identifier.
     *
     * @param id identifier for a movie.
     * @return Movie found with id. null if nothing is found.
     */
    @Override
    public OMDBMovie getMovie(String id) {
        return new RestTemplate().getForObject("https://www.omdbapi.com/?apikey=" + APIKEY + "&i=" + id, OMDBMovie.class);
    }

    /**
     * Search for a movie given given query.
     *
     * @param q The Query to find matching info.
     * @return A list of relevant movies.
     */
    @Override
    public List<OMDBMovie> searchMovie(MovieSearchQuery q) {
        return null;
    }

    /**
     * Convert from Movie to a matching external source as it would be from
     * calling getMovie(id of mov).
     *
     * @param mov Movie to be converting external source
     * @return T movie as represented as T.
     */
    @Override
    public OMDBMovie movieToExternalSource(Movie mov) {
        return null;
    }

    /**
     * Covert from T to a Movie with all the data filled in.
     *
     * @param omdbMovie External data to be converted to Movie.
     * @return Movie with all the info filled in using data from t.
     */
    @Override
    public Movie externalSourceToMovie(OMDBMovie omdbMovie) {
        return null;
    }
}
