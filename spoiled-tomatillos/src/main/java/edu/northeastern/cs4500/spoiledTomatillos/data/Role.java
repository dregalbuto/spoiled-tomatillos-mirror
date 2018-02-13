package edu.northeastern.cs4500.spoiledTomatillos.data;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * Class for a user role
 */
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
    @ManyToMany(mappedBy = "roles")
	private Collection<User> users;
    
    /**
     *  collection of all privileges tied to this role
     *  (might get rid of privileges)
     */
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
	
	/**
	 * @param name Name of this role
	 */
	public Role(String name) {
		this.setName(name);
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
