package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;

/**
 * Review repository
 */
public interface ReviewRepository extends JpaRepository <Review, Integer>{
  List<Review> findByMovieAndUserIsNull(Movie movie);
}
