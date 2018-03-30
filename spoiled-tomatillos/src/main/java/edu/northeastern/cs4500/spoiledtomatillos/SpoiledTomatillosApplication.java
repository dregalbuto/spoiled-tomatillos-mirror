package edu.northeastern.cs4500.spoiledtomatillos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Slf4j
public class SpoiledTomatillosApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpoiledTomatillosApplication.class, args);
		log.info("This is an example info message");
		log.warn("This is an example warning message");
		log.error("This is an example error message");
	}
}
