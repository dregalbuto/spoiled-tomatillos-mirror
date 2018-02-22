package edu.northeastern.cs4500.spoiledTomatillos.user.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests for the Role class
 *
 */
public class RoleTest {
	@Autowired
	private Role r;
	
	@Test
	public void testGetSetName() {
		r = new Role();
		r.setName("ROLE_USER");
		assertEquals("ROLE_USER", r.getName());
	}
	
	@Test
	public void testGetSetPrivileges() {
		Privilege p = new Privilege();
		p.setName("PRIV_READ");
		Collection<Privilege> privs = new ArrayList<Privilege>();
		privs.add(p);
		
		r = new Role();
		r.setPrivileges(privs);
		
		Collection<Privilege> resultPrivs = r.getPrivileges();
		
		assertTrue(resultPrivs.contains(p));
	}
}
