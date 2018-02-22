package edu.northeastern.cs4500.spoiledTomatillos.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.web.dto.UserRegistrationDto;

/**
 * A service that goes between the web and the back-end
 *
 */
public interface UserService extends UserDetailsService {
	User findByEmail(String email);
	User findByUsername(String username);
	User save(UserRegistrationDto registration);
	List<User> getAllUsers();
}
