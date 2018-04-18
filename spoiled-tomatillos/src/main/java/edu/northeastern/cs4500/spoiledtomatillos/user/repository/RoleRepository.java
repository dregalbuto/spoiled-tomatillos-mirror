package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Role
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Find Role by name.
     * @param name Name of the role.
     * @return Role if found.
     */
    public Role findByName(String name);
}
