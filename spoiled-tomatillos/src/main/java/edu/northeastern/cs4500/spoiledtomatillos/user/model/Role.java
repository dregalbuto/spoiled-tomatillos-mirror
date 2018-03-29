package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import java.util.Collection;

import javax.persistence.*;

import lombok.Data;

/**
 * Class for a user role
 */
@Data
@Entity(name="roles")
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
	
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
	private Collection<User> users;
    
    /**
     *  collection of all privileges tied to this role
     *  (might get rid of privileges)
     */
	
	@ManyToMany(cascade = CascadeType.ALL)
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
