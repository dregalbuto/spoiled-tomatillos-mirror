package edu.northeastern.cs4500.spoiledTomatillos.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>, RoleRepositoryCustom {
	Role findByName(String name);
}
