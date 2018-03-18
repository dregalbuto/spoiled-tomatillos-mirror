package edu.northeastern.cs4500.spoiledTomatillos.movies;

import edu.northeastern.cs4500.spoiledTomatillos.movies.omdb.OMDBMovieSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Cached Repository that uses movie repository as a cache. Use cache first
 * before look up.
 */
@Service
public class MovieCachedRepository {
    private MovieRepository movieRepository;
    private List<ExternalMovieSource> sources;
    /**
     * Constructor to create a MovieCachedRepository with needed repo.
     * @param movieRepository Cache to use for Movie.
     */
    @Autowired
    public MovieCachedRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.sources = new ArrayList<>();
        this.sources.add(new OMDBMovieSource());
    }

    /**
     * Get the movie based on id from either cache or gathered from the sources.
     * @param id ID of the movie to get.
     * @return Movie with data if found in the system. Null otherwise.
     */
    @Transactional
    public Movie getMovie(String id) {
        if (id == null) {
            return null;
        }
        if (this.movieRepository.exists(id)) {
            return this.movieRepository.findOne(id);
        }
        for (ExternalMovieSource ems : this.sources) {
            Movie movie = ems.getMovie(id);
            if (movie != null) {
                this.movieRepository.saveAndFlush(movie);
                return movie;
            }
        }
        return null;
    }

    /**
     * Search for movies based on the query data from either cache, or
     * refreshing data source.
     * @param q Query information to get movie.
     * @return List of best matched movie.
     */
    @Transactional
    public List<String> searchMovie(MovieSearchQuery q) {
        List<String> result = new ArrayList<>();
        if (q == null) {
            return result;
        }
        for (Movie movie : this.movieRepository.findAll()) {
            if (movie.getTitle().contains(q.getTitle())) {
                result.add(movie.getId());
            }
        }
        for (ExternalMovieSource ems : this.sources) {
            result.addAll(ems.searchMovie(q));
        }
        return result;
    }
}
