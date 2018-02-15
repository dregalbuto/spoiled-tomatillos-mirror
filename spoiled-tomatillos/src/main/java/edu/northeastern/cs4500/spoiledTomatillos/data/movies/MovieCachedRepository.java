package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Cached Repository that uses movie repository as a cache. Use cache first
 * before loop up.
 */
@Service
public class MovieCachedRepository {
    private MovieRepository movieRepository;

    /**
     * Constructor to create a MovieCachedRepository with needed repo.
     * @param movieRepository Cache to use for Movie.
     */
    @Autowired
    public MovieCachedRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Get the movie based on id from either cache or gathered from the sources.
     * @param id ID of the movie to get.
     * @return Movie with data if found in the system. Null otherwise.
     */
    @Transactional
    public Movie getMovie(String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Search for movies based on the query data from either cache, or
     * refreshing data source.
     * @param q Query information to get movie.
     * @return List of best matched movie.
     */
    @Transactional
    public List<Movie> searchMovie(MovieSearchQuery q) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
