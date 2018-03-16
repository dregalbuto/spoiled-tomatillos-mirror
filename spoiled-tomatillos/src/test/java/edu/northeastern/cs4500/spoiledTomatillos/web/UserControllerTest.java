package edu.northeastern.cs4500.spoiledTomatillos.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


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
