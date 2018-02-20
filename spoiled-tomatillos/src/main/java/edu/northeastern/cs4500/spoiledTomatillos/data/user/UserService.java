package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import java.util.List;

public interface UserService {
	User getUserByUsername(String username);
	List<User> getAllUsers();
}
