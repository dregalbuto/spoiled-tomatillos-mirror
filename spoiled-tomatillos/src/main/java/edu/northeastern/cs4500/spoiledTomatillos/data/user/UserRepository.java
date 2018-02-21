package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
