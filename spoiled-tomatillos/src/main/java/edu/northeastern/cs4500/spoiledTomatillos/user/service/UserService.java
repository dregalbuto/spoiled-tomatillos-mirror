package edu.northeastern.cs4500.spoiledTomatillos.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	User findByEmail(String email);
	User findByUsername(String username);
	User save(UserRegistrationDto registration);
	List<User> getAllUsers();
}
