package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Find user by email.
     * @param email email to find.
     * @return User with matching email.
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by username.
     * @param username username to find.
     * @return User with matching username.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by id.
     * @param id id to find.
     * @return User with matching id.
     */
    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * called by UserRegistration controller to save a user to db
     * @param user user to save.
     */
    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    /**
     * Get all the users.
     * @return all users in repository.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Search user by given string.
     * @param q String to quarry the database.
     * @return List of matching users.
     */
    public List<User> searchUser(String q) {
        List<User> result = new ArrayList<>();
        if (q == "") {
            return result;
        }
        for (User user : getAllUsers()) {
            if (user.getEmail().contains(q)
                    || user.getUsername().contains(q)
                    || user.getFirstName().contains(q)
                    || user.getLastName().contains(q)) {
                result.add(user);
            }
        }
        return result;
    }
}
