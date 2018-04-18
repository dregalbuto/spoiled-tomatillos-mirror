package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for FriendList
 */
public interface FriendListRepository extends JpaRepository<FriendList, Integer> {
    // Default function in JPA
}
