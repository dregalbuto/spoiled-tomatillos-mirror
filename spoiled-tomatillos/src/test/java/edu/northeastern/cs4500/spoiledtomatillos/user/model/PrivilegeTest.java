package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

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
