package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;

public interface UserService {//extends UserDetailsService {
	User findByEmail(String email);
	User findByUsername(String username);
	User findById(int id);
	void save(User newuser);
	List<User> getAllUsers();
}
