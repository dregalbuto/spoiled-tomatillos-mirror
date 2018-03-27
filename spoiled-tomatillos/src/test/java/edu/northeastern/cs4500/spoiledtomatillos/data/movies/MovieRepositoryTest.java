package edu.northeastern.cs4500.spoiledtomatillos.data.movies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.spoiledtomatillos.data.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.data.movies.MovieRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testMovie() {
        assertEquals(0, movieRepository.count());
        Movie mov = new Movie();
        String movID = "tt000012";
        String movT = "Name of movie";
        mov.setId(movID);
        mov.setTitle(movT);
        entityManager.persist(mov);
        assertEquals(mov, movieRepository.findOne(movID));
        assertEquals(movT, movieRepository.findOne(movID).getTitle());
        assertEquals(true, movieRepository.exists(movID));
        movieRepository.delete(movID);
        assertEquals(0, movieRepository.count());
    }
}
