package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.Data;

/**
 * Class for a user of Spoiled Tomatillos
 */

@Data
@Entity(name="users")
@CrossOrigin(origins = "http://localhost:3000")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

    @Column(name = "first_name")
	private String firstName;

    @Column(name = "last_name")
	private String lastName;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private boolean token_expired;
	
	/**
	 * All of the roles this user has
	 */
	@ManyToMany
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
	
	public User() {
		
	}
	
	/*
	 May add roles into constructor later
	 */
	public User(String firstName, String lastName, String email, 
			String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.setPassword(password);
		this.enabled = true;
		this.token_expired = false;
	}
	
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		boolean out = false;
		if (BCrypt.checkpw(plainPassword, hashedPassword)) {
			out = true;
			System.out.println("The password matches.");
		}
		else {
			out = false;
			System.out.println("The password does not match.");
		}
		return out;
	}
	
}
