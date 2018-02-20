package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	public List<User> findByUsername(String username);
}
