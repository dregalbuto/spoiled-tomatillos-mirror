package edu.northeastern.cs4500.spoiledTomatillos.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);
}
