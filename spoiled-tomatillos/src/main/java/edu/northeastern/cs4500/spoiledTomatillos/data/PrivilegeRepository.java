package edu.northeastern.cs4500.spoiledTomatillos.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>, PrivilegeRepositoryCustom {

}
