package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import java.util.Collection;

import javax.persistence.*;

import edu.northeastern.cs4500.spoiledTomatillos.data.reviews.Review;
import lombok.Data;

/**
 * Class for a user of Spoiled Tomatillos
 */
@Data
@Entity(name="users")
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
    private Collection<Role> roles;

	/**
	 * A user has many reviews.
	 */
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private Collection<Review> reviews;

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