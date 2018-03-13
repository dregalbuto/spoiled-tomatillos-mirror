package edu.northeastern.cs4500.spoiledTomatillos.user.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private boolean tokenExpired;
	
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
	@JsonManagedReference
    private Collection<Role> roles;
	
	public User() {
		
	}	
	
	public User(String firstName, String lastName, String email, 
			String username, String password, boolean enabled, 
			boolean tokenExpired) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.tokenExpired = tokenExpired;
	}
}