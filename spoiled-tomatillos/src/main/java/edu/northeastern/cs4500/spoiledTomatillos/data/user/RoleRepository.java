package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);
}
