package edu.northeastern.cs4500.spoiledtomatillos.movies;

import edu.northeastern.cs4500.spoiledtomatillos.movies.omdb.OMDBMovieSource;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.ReviewRepository;
import edu.northeastern.cs4500.spoiledtomatillos.web.MovieSearchQuery;
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
    private ReviewRepository reviewRepository;
    private List<ExternalMovieSource> sources;

    /**
     * Constructor to create a MovieCachedRepository with needed repo.
     *
     * @param movieRepository Cache to use for Movie.
     */
    @Autowired
    public MovieCachedRepository(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.sources = new ArrayList<>();
        this.sources.add(new OMDBMovieSource());
        //Add more external movie sources here.
    }

    /**
     * Get the movie based on id from either cache or gathered from the sources.
     *
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
                movie = this.movieRepository.saveAndFlush(movie);
                for (Review review : ems.importCriticReview(movie)) {
                    this.reviewRepository.saveAndFlush(review);
                }
                return movie;
            }
        }
        return null;
    }

    /**
     * Search for movies based on the query data from either cache, or
     * refreshing data source.
     *
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
