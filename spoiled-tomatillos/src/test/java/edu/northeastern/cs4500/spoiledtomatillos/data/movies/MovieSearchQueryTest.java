package edu.northeastern.cs4500.spoiledtomatillos.data.movies;

import org.junit.Before;
import org.junit.Test;

import edu.northeastern.cs4500.spoiledtomatillos.data.movies.MovieSearchQuery;

import static org.junit.Assert.*;

public class MovieSearchQueryTest {

    MovieSearchQuery sq;
    String movieTitle = "Title";

    @Before
    public void setUp() {
        movieTitle = "Title";
        sq = new MovieSearchQuery(movieTitle);
    }

    @Test
    public void getTitle() {
        assertEquals(sq.getTitle(), movieTitle);
    }

    @Test
    public void constructor() {
        // Should not accept null
        boolean error = false;
        try {
            sq = new MovieSearchQuery(null);
        } catch (Exception ex) {
            error = true;
        }
        assertEquals(true, error);
    }

    @Test
    public void setTitle() {
        assertEquals(movieTitle, sq.getTitle());
        sq.setTitle("Alt");
        assertEquals("Alt", sq.getTitle());

        boolean error = false;
        try {
            sq.setTitle(null);
        } catch (Exception ex) {
            error = true;
        }
        assertEquals(true, error);
    }
}