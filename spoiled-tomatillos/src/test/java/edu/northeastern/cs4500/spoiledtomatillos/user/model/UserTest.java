package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserTest {
	@Autowired
	User u;
	
	@Test
	public void testFirstName() {
		u = new User();
		u.setFirst_name("test");
		assertEquals("test", u.getFirst_name());
	}
	
	@Test
	public void testLastName() {
		u = new User();
		u.setLast_name("test");
		assertEquals("test", u.getLast_name());
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
		u = new User("test_first", "test_last", "test@test",
                "test_name", "superSecret");
        assertTrue(u.checkPassword("superSecret"));
        assertTrue(!u.checkPassword(""));
        assertTrue(!u.checkPassword(null));
        assertTrue(!u.checkPassword("not the right password"));
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
	public void testToken() throws IllegalAccessException {
	    Date d = new Date();
        u = new User("test_first", "test_last", "test@test",
                "test_name", "superSecret");
        boolean err = false;
        try {
            u.getToken("wrong");
        } catch (IllegalAccessException ex) {
            err = true;
        }
        assertTrue(err);
        assertFalse(u.validToken("wrong"));
		u.setTokenExpired();
        err = false;
		try {
            u.getToken("wrong");
        } catch (IllegalAccessException ex) {
		    err = true;
        }
        assertTrue(err);
        err = false;
        assertFalse(u.validToken("wrong"));
        u.setTokenExpired();
        String token = u.getToken("superSecret");
        assertTrue(!token.equals(""));
        assertTrue(u.validToken(token));
        u.setTokenExpired();
        assertFalse(u.validToken(token));
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
