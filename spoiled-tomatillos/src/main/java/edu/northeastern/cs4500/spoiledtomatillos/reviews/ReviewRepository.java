package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Review repository
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /**
     * Find review that is only made by critics so user is NULL.
     * @param movie Movie to search.
     * @return List of reviews for given Movie made by critics.
     */
    List<Review> findByMovieAndUserIsNull(Movie movie);
}
