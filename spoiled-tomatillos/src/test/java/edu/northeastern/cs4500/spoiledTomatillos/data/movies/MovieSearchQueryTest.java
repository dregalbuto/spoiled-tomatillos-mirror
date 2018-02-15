package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieSearchQueryTest {

    MovieSearchQuery sq;

    @Before
    public void setUp() throws Exception {
        sq = new MovieSearchQuery("Title");
    }

    @Test
    public void getTitle() {
        assertEquals(sq.getTitle(), "Title");
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
        assertEquals(sq.getTitle(), "Title");
        sq.setTitle("Alt");
        assertEquals(sq.getTitle(), "Alt");

        boolean error = false;
        try {
            sq.setTitle(null);
        } catch (Exception ex) {
            error = true;
        }
        assertEquals(true, error);
    }
}