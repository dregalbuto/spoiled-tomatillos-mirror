package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for User.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Find user by username.
     * @param username username to find.
     * @return User with given username if found.
     */
    User findByUsername(String username);

    /**
     * Find user by email.
     * @param email email to find.
     * @return User with given email if found.
     */
    User findByEmail(String email);

    /**
     * Find user by id.
     * @param id id to find.
     * @return User with given id if found.
     */
    User findById(int id);
}
