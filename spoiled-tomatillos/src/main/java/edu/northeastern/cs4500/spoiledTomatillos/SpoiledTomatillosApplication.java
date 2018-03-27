package edu.northeastern.cs4500.spoiledTomatillos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SpoiledTomatillosApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpoiledTomatillosApplication.class, args);
	}
}
