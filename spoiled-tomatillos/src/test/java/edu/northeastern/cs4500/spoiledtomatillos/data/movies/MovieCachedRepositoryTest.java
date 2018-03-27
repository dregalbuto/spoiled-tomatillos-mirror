package edu.northeastern.cs4500.spoiledtomatillos.data.movies;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.spoiledtomatillos.data.movies.MovieCachedRepository;
import edu.northeastern.cs4500.spoiledtomatillos.data.movies.MovieRepository;
import edu.northeastern.cs4500.spoiledtomatillos.data.movies.MovieSearchQuery;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test for the cached repository.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieCachedRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    MovieCachedRepository movieCachedRepository;

    @Before
    public void setUp() {
        entityManager.clear();
        movieCachedRepository = new MovieCachedRepository(movieRepository);
    }

    @Test
    public void getMovie() {
        String id = "tt0000001";
        assertEquals(null, this.movieCachedRepository.getMovie(null));
        assertEquals(id, this.movieCachedRepository.getMovie(id).getId());
        assertEquals("Carmencita", this.movieCachedRepository.getMovie(id).getTitle());
    }

    @Test
    public void searchMovie() {
        String id = "tt0000001";
        assertEquals(new ArrayList<>(), this.movieCachedRepository.searchMovie(null));
        String str = "mencit";
        assertEquals(id, this.movieCachedRepository.getMovie(id).getId());
        assertEquals(true,
                this.movieCachedRepository.searchMovie(new MovieSearchQuery(str)).contains(id));
        assertEquals(true,
                this.movieCachedRepository
                        .searchMovie(new MovieSearchQuery(str + "asd")).isEmpty());
        assertEquals(true, this.movieCachedRepository
                .searchMovie(new MovieSearchQuery("Le clown et ses chiens")).contains("tt0000002"));
    }

}