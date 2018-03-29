package edu.northeastern.cs4500.spoiledtomatillos.groups;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for groups
 */
public interface GroupRepository extends JpaRepository<Group, Integer> {
  // Default function in JPA
}
