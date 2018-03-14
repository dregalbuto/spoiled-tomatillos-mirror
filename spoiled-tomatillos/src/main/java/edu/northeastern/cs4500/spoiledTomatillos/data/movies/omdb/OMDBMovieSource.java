package edu.northeastern.cs4500.spoiledTomatillos.data.movies.omdb;

import edu.northeastern.cs4500.spoiledTomatillos.data.movies.ExternalMovieSource;
import edu.northeastern.cs4500.spoiledTomatillos.data.movies.Movie;
import edu.northeastern.cs4500.spoiledTomatillos.data.movies.MovieSearchQuery;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class OMDBMovieSource implements ExternalMovieSource {
    private final String APIKEY = "3ef62c6c";

    /**
     * Get movie from external source using given identifier.
     *
     * @param id identifier for a movie.
     * @return Movie found with id. null if nothing is found.
     */
    @Override
    public Movie getMovie(String id) {
        if (id == null) {
            return null;
        }
        OMDBMovie omdbMovie = new RestTemplate().getForObject(
                "http://www.omdbapi.com/?apikey={key}&i={id}", OMDBMovie.class, APIKEY, id);
        if (omdbMovie.getImdbID() == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setActors(omdbMovie.getActors());
        movie.setDescription(omdbMovie.getPlot());
        movie.setGenres(omdbMovie.getGenre());
        movie.setId(omdbMovie.getImdbID());
        movie.setImgURL(omdbMovie.getPoster());
        movie.setReleaseDate(omdbMovie.getReleased());
        movie.setRuntimeMinuets(omdbMovie.getRuntime());
        movie.setSource("OMDB");
        movie.setTitle(omdbMovie.getTitle());
        movie.setTitleType(omdbMovie.getType());
        return movie;
    }

    /**
     * Search for a movie given given query.
     *
     * @param q The Query to find matching info.
     * @return A list of relevant movies.
     */
    @Override
    public List<String> searchMovie(MovieSearchQuery q) {
        List<String> ids = new ArrayList<>();
        if (q == null) {
            return ids;
        }
        OMDBMovieSearch movies = new RestTemplate().getForObject(
                "http://www.omdbapi.com/?apikey={key}&s={qs}", OMDBMovieSearch.class, APIKEY, q.getTitle());
        if (movies.getSearch() != null) {
            for (OMDBMovieSearchElement ose : movies.getSearch()) {
                ids.add(ose.getImdbID());
            }
        }
        return ids;
    }

    /**
     * Convert from Movie to a matching external source as it would be from
     * calling getMovie(id of mov).
     *
     * @param mov Movie to be converting external source
     * @return T movie as represented as T.
     */
    /**
    @Override
    public OMDBMovie movieToExternalSource(@NonNull Movie mov) {
        return null;
    }
    */
    /**
     * Covert from T to a Movie with all the data filled in.
     *
     * @param omdbMovie External data to be converted to Movie.
     * @return Movie with all the info filled in using data from t.
     */
    /**
    @Override
    public Movie externalSourceToMovie(OMDBMovie omdbMovie) {
        return null;
    }
     */
}
