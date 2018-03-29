package edu.northeastern.cs4500.spoiledtomatillos.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Review repository
 */
public interface ReviewRepository extends JpaRepository <Review, Integer>{
}
