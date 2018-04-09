package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import java.util.List;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;

public interface UserService {
	User findByEmail(String email);
	User findByUsername(String username);
	User findById(int id);
	void save(User newuser);
	List<User> getAllUsers();
	List<User> searchUser(String q);
}
