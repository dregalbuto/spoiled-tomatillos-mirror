package edu.northeastern.cs4500.spoiledtomatillos.recommendations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, String>{
    // Only has default methods from JPARepository
}
