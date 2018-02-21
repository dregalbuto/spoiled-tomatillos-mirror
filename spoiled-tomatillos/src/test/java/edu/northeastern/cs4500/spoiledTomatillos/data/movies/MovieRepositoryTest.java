package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

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
        assertEquals(movieRepository.count(), 0);
        Movie mov = new Movie();
        String movID = "tt000012";
        String movT = "Name of movie";
        mov.setId(movID);
        mov.setTitle(movT);
        entityManager.persist(mov);
        assertEquals(movieRepository.findOne(movID), mov);
        assertEquals(movieRepository.findOne(movID).getTitle(), movT);
        assertEquals(movieRepository.exists(movID), true);
        movieRepository.delete(movID);
        assertEquals(movieRepository.count(), 0);
    }
}
