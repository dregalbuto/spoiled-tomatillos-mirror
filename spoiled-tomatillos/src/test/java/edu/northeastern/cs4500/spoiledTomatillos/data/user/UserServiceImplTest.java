package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.repository.UserRepository;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
	@TestConfiguration
    static class UserServiceImplTestContextConfiguration {
  
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
	
	@Autowired
    private UserService userService;
 
    @MockBean
    private UserRepository userRepository;
    
    @Before
    public void setUp() {
        User diana = new User();
        diana.setUsername("diana");
     
        Mockito.when(userRepository.findByUsername(diana.getUsername()).get(0))
          .thenReturn(diana);
    }
    
    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        String username = "diana";
        User found = userService.loadUserByUsername("diana");
      
         assertEquals(found.getUsername(), username);
     }
}
