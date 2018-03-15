package edu.northeastern.cs4500.spoiledTomatillos.user.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

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
	private String first_name;
	private String last_name;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private boolean tokenExpired;
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isTokenExpired() {
		return tokenExpired;
	}
	
	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}
	
	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}
	
	public String getFirstName() {
		return first_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}
	
	public String getLastName() {
		return last_name;
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
	public User(String first_name, String last_name, String email, 
			String username, String password) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.username = username;
		this.setPassword(password);
	}
	
}