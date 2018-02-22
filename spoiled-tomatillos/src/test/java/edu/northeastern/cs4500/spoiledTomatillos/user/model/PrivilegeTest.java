package edu.northeastern.cs4500.spoiledTomatillos.user.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests for the Privilege class
 *
 */
public class PrivilegeTest {
	@Autowired
	private Privilege p;
	
	@Test
	public void testGetSetName() {
		p = new Privilege();
		p.setName("PRIV_READ");
		assertEquals("PRIV_READ", p.getName());
	}
}
