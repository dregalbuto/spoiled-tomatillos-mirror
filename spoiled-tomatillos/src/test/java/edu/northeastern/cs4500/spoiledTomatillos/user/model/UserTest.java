package edu.northeastern.cs4500.spoiledTomatillos.user.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest {
	@Autowired
	User u;
	
	@Test
	public void testFirstName() {
		u = new User();
		u.setFirstName("test");
		assertEquals("test", u.getFirstName());
	}
	
	@Test
	public void testLastName() {
		u = new User();
		u.setLastName("test");
		assertEquals("test", u.getLastName());
	}
	
	@Test
	public void testEmail() {
		u = new User();
		u.setEmail("test@test.net");
		assertEquals("test@test.net", u.getEmail());
	}
	
	@Test
	public void testUsername() {
		u = new User();
		u.setUsername("test");
		assertEquals("test", u.getUsername());
	}
	
	@Test
	public void testPassword() {
		u = new User();
		u.setPassword("superSecret");
		assertEquals("superSecret", u.getPassword());
	}
	
	@Test
	public void testEnabled() {
		u = new User();
		u.setEnabled(true);
		assertTrue(u.isEnabled());
		u.setEnabled(false);
		assertFalse(u.isEnabled());
	}
	
	@Test
	public void testTokenExpired() {
		u = new User();
		u.setTokenExpired(true);
		assertTrue(u.isTokenExpired());
		u.setTokenExpired(false);
		assertFalse(u.isTokenExpired());
	}
	
	@Test
	public void testGetSetRoles() {
		Role r = new Role();
		r.setName("ROLE_USER");
		Collection<Role> roles = new ArrayList<Role>();
		roles.add(r);
		
		u = new User();
		u.setRoles(roles);
		
		Collection<Role> resultRoles = u.getRoles();
		
		assertTrue(resultRoles.contains(r));
	}
}
