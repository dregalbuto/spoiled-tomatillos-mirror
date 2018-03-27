package edu.northeastern.cs4500.spoiledtomatillos.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.UserRepository;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserServiceImpl;

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
}
