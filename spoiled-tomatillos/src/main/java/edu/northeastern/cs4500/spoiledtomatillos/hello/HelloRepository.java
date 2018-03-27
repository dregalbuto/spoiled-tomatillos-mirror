package edu.northeastern.cs4500.spoiledtomatillos.hello;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository 
	extends JpaRepository<HelloObject, Integer>{

}
