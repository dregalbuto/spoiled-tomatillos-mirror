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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * Class for a user role
 */
@Data
@Entity(name="roles")
@CrossOrigin(origins = "http://localhost:3000")
public class Role {
	/**
	 * unique ID number for this role (used in database)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * name of the role
	 * example ROLE_ADMIN, ROLE_USER
	 */
	private String name;
	
	/**
	 *  collection of all users who have this role
	 */
	@JsonBackReference
    @ManyToMany(mappedBy = "roles")
	private Collection<User> users;
    
    /**
     *  collection of all privileges tied to this role
     *  (might get rid of privileges)
     */
	@JsonManagedReference
	@ManyToMany
	@JoinTable(
	        name = "roles_privileges", 
	        joinColumns = @JoinColumn(
	          name = "role_id", referencedColumnName = "id"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "privilege_id", referencedColumnName = "id"))
	private Collection<Privilege> privileges;
	
	/**
	 * Default constructor
	 */
	public Role() {
		this.setName(new String());
	}
	
	public Role(String name) {
		this.name = name;
	}
	
	/**
	 * @param privileges List of privileges for this role
	 */
	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

	/**
	 * @return Name of this role
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name New name for this role
	 */
	public void setName(String name) {
		this.name = name;
	}
}
