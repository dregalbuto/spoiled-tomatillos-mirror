package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * Class for a privilege within Spoiled Tomatillos
 * Might get rid of this and just implement it within the Role class
 */
@Data
@Entity(name="privileges")
public class Privilege {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    private String name;
 
    /**
     * All of the roles that have this privilege
     */
    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private Collection<Role> roles;
    
    public Privilege() {
    		this.setName(new String());
    }
    
    public Privilege(String name) {
    		this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
