package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for a movie.
 */
public interface MovieRepository extends JpaRepository<Movie, String> {
    //Only have default methods from JPARepository.
}
