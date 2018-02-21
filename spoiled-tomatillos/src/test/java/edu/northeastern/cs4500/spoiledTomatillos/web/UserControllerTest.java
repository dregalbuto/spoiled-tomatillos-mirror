package edu.northeastern.cs4500.spoiledTomatillos.web;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;
import edu.northeastern.cs4500.spoiledTomatillos.web.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private UserService service;
 
    // write test cases here
    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray()
      throws Exception {
         
        User diana = new User();
        diana.setUsername("diana");
        
        List<User> allUsers = Arrays.asList(diana);
     
       // given(service.getAllUsers()).willReturn(allUsers);
     
       
    }
}
