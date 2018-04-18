package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Repository for Privilege.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    /**
     * Find a privilege by name.
     * @param name name of the privilege.
     * @return the Privilege if found.
     */
    @Transactional
    public Privilege findByName(String name);
}
