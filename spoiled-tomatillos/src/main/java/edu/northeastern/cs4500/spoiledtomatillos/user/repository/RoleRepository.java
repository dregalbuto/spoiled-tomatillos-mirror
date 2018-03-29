package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);
}
