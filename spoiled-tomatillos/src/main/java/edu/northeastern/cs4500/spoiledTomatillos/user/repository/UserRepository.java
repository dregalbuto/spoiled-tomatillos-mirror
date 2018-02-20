package edu.northeastern.cs4500.spoiledTomatillos.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public List<User> findByUsername(String username);
	User findByEmail(String email);
}
