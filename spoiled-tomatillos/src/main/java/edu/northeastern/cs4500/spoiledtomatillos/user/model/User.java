package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Class for a user of Spoiled Tomatillos
 */
@Data
@Entity(name="users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
	private int id;
    @JsonProperty(value = "first_name")
	private String first_name;
    @JsonProperty(value = "last_name")
	private String last_name;
    @JsonProperty(value = "email")
	private String email;
    @JsonProperty(value = "username")
	private String username;
    //Can add @JsonProperty(value = "password")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
	private String password;
    @JsonProperty(value = "enabled")
	private boolean enabled;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
	private String token;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
	private long tokenExpiration;
	
	/**
	 * All of the roles this user has
	 */
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
    @JsonProperty(value = "roles")
    private Collection<Role> roles;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(value = "reviews")
	private Collection<Review> reviews;

	public User() {
		// Empty constructor for user.
	}

	/**
	 * Constructs a User with first, last name, email, username, encrypted
	 * of given password with no permission and is enabled.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param username
	 * @param password
	 */
	public User(String firstName, String lastName, String email, 
			String username, String password) {
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.username = username;
		this.setPassword(password);
		this.enabled = true;
		this.token = "";
		this.tokenExpiration = 0;
	}

	/**
	 * Given password in plain text and saves it encrypted.
	 * @param password user password in plain text.
	 */
	private void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public boolean checkPassword(String plainPassword) {
		return BCrypt.checkpw(plainPassword, this.password);
	}

    /**
     * Get the current token if the password is valid. Token will have at least
     * 10 minuets before it expires.
     * @param plainPassword Plain text password of this user.
     * @return token to access the user.
     * @throws IllegalAccessException If the password is wrong or user is disabled.
     */
	public String getToken(String plainPassword) throws IllegalAccessException {
        if (!this.isEnabled()) {
            throw new IllegalAccessException("User is disabled");
        }
	    if (this.checkPassword(plainPassword)) {
            this.updateTokenExpiration(600000);
	        return this.token;
        }
        throw new IllegalAccessException("Do not have permission to access token");
    }

    /**
     * Update the expiration of the current token or create a new one if expired.
     * @param time how long until the current token expires.
     */
	private void updateTokenExpiration(long time) {
	    if (this.isTokenExpired()) {
	        this.token = UUID.randomUUID().toString();
        }
        this.tokenExpiration = new Date().getTime() + time;
    }

    /**
     * Check if current token is expired.
     * @return true if current token is expired.
     */
    private boolean isTokenExpired() {
	    return new Date().after(new Date(this.tokenExpiration));
    }

    /**
     * Set the current token as expired.
     */
    public void setTokenExpired() {
        if (!isTokenExpired()) {
            this.tokenExpiration = 0;
        }
    }

    /**
     * Check if the given token is valid. Make sure it has at least 10 minuets
     * before expiring.
     * @param token String to check if it is a valid token.
     * @return true if given token is right and not expired.
     */
    public boolean validToken(String token) {
        if (!this.isTokenExpired() && this.token.equals(token)) {
            this.updateTokenExpiration(600000);
            return true;
        }
        return false;
    }
}
