package edu.northeastern.cs4500.spoiledTomatillos.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	User findByUsername(String username);
	User findByEmail(String email);
}
