package edu.northeastern.cs4500.spoiledTomatillos.data;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name="privileges")
public class Privilege {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    private String name;
 
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
    public Privilege() {
    		this.setName(new String());
    }
    
    public Privilege(String name) {
    		this.setName(name);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
