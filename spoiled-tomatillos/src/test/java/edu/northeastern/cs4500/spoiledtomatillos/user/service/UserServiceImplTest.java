package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.UserRepository;

/**
 * Tests for the User Service class
 * The service needs to communicate with the back-end without knowing how it works
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository mockUserRepository;
	
	@Test
    public void testFindUser(){
        User testUser = new User();
        testUser.setUsername("test123");
        when(mockUserRepository.findByUsername("test123")).thenReturn(testUser);
        
        User result = userService.findByUsername("test123");
        assertThat("result", result, is(sameInstance(testUser)));
        verify(mockUserRepository).findByUsername("test123");
    }
	
	@Test
	public void testFindUserByUsername() {
		User testUser = new User();
		testUser.setUsername("test123");
		testUser.setEmail("user@test.com");
		when(mockUserRepository.findByEmail("user@test.com")).thenReturn(testUser);
		User result = userService.findByEmail("user@test.com");
		assertEquals("test123", result.getUsername());
	}
	
	@Test
	public void testFindById() {
		User testUser = new User();
		testUser.setId(12345);
		testUser.setUsername("Diana");
		when(mockUserRepository.findById(12345)).thenReturn(testUser);
		User result = userService.findById(12345);
		assertEquals("Diana", result.getUsername());
	}
	
	@Test
	public void testGetAllUsers() {
		User testUser = new User();
		testUser.setId(12345);
		testUser.setUsername("Diana");
		User testUser2 = new User();
		testUser2.setId(666);
		testUser2.setUsername("Test");
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(testUser2);
		
		when(mockUserRepository.findAll()).thenReturn(users);
		List<User> result = userService.getAllUsers();
		
		assertEquals(2, result.size());
		assertEquals("Diana", result.get(0).getUsername());
		
		
	}
}
