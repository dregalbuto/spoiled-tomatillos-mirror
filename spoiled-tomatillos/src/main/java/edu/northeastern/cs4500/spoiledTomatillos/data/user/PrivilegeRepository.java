package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
	@Transactional
	public Privilege findByName(String name);
}
