package edu.northeastern.cs4500.spoiledtomatillos.movies.omdb;

import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.web.MovieSearchQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OMDBMovieSourceTest {

    OMDBMovieSource omdbMovieSource;

    @Before
    public void setUp() throws Exception {
        omdbMovieSource = new OMDBMovieSource();
    }

    @Test
    public void getMovie() {
        String id = "tt0000001";
        Movie movie = omdbMovieSource.getMovie(id);
        assertEquals(id, movie.getId());
        assertEquals("Carmencita", movie.getTitle());
        assertEquals(null, omdbMovieSource.getMovie(null));
    }

    @Test
    public void searchMovie() {
        String id = "tt0000001";
        assertEquals(id, omdbMovieSource.searchMovie(new MovieSearchQuery("Carmencita")).get(0));
        assertEquals(new ArrayList<>(), omdbMovieSource.searchMovie(null));
    }

    @Test
    public void importCriticReview() {
        String id = "tt0000001";
        Movie movie = omdbMovieSource.getMovie(id);
        List<Review> reviews = omdbMovieSource.importCriticReview(movie);
        assertEquals(1, reviews.size());
        assertEquals(new Review("Internet Movie Database", 2, movie, null), reviews.get(0));
    }
}