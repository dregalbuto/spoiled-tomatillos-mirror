package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendReposiitory extends JpaRepository<User, Integer> {
}
