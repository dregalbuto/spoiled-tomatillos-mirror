package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;

import java.util.List;

/**
 * User service.
 */
public interface UserService {
    /**
     * Find user by email.
     * @param email email to find.
     * @return User with given email if found.
     */
    User findByEmail(String email);

    /**
     * Find user by username.
     * @param username username to find.
     * @return User with given username if found.
     */
    User findByUsername(String username);

    /**
     * Find user by id.
     * @param id id to find.
     * @return User with given id if found.
     */
    User findById(int id);

    /**
     * User to save.
     * @param newuser user to save.
     */
    void save(User newuser);

    /**
     * Get all the users.
     * @return list of all the users.
     */
    List<User> getAllUsers();

    /**
     * Search for a user by given string.
     * @param q String to quarry the database.
     * @return List of matching users.
     */
    List<User> searchUser(String q);
}
