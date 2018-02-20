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
     
        Mockito.when(userRepository.findFirstOrderByUsername(diana.getUsername()))
          .thenReturn(diana);
    }
    
    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        String username = "diana";
        User found = userService.getUserByUsername("diana");
      
         assertEquals(found.getUsername(), username);
     }
}
