package edu.northeastern.cs4500.spoiledtomatillos.movies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.ReviewRepository;
import edu.northeastern.cs4500.spoiledtomatillos.web.MovieSearchQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for the cached repository.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieCachedRepositoryTest {

    MovieCachedRepository movieCachedRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        entityManager.clear();
        movieCachedRepository = new MovieCachedRepository(movieRepository, reviewRepository);
    }

    @Test
    public void getMovie() throws JsonProcessingException {
        String id = "tt0000001";
        assertEquals(null, this.movieCachedRepository.getMovie(null));
        assertEquals(id, this.movieCachedRepository.getMovie(id).getId());
        Movie movie = this.movieCachedRepository.getMovie(id);
        assertEquals("Carmencita", movie.getTitle());
        List<Review> reviewList = this.reviewRepository.findByMovieAndUserIsNull(movie);
        assertEquals(1, reviewList.size());
        assertEquals("{\"id\":2,\"text\":\"Internet Movie Database\",\"rating\":2," +
                        "\"movie\":{\"id\":\"tt0000001\",\"title\":\"Carmencita\"},\"critic\":true}",
                new ObjectMapper().writeValueAsString(reviewList.get(0)));
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