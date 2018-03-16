package edu.northeastern.cs4500.spoiledTomatillos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.View;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;
import edu.northeastern.cs4500.spoiledTomatillos.web.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
 
    @Test
    public void info() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/username/erin.z"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1000000,"
                        		+"\"first_name\":\"erin\"," 
                        		+"\"last_name\":\"zhang\","
                             + "\"email\":\"erinzhang@husky.neu.edu\","
                             +"\"username\":\"erin.z\","
                             +"\"password\":\"password\"," 
                             +"\"enabled\":true," 
                             +"\"token_expired\":false,"
                             + "\"roles\":[]}"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/username/someRandomUser"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}
