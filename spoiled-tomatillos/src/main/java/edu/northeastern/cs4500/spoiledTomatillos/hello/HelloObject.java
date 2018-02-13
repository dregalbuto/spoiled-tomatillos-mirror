package edu.northeastern.cs4500.spoiledTomatillos.hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Simple example of CRU services as an object
 */
@Entity(name="test")
public class HelloObject {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private String message;
	
	/**
	 * Default constructor that gives the object an empty string
	 */
	public HelloObject() {
		this.message = new String();
	}
	
	/** Constructor with initial message
	 * @param message: The message this object should contain
	 */
	public HelloObject(String message) {
		this.message = message;
	}
	
	/** Setter for Hello World POJO
	 * @param message: The message this object should contain
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/** Getter for Hello World POJO
	 * @return The message this object contains
	 */
	public String getMessage() {
		return message;
	}
}
