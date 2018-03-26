package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	User findByEmail(String email);
	User findById(int id);
}
