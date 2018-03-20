package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void registerAndInfo() throws Exception {
        JSONObject request = new JSONObject();
        request.put("first_name", "erin");
        request.put("last_name", "zhang");
        request.put("email", "erinzhang@husky.neu.edu");
        request.put("username", "erin.z");
        request.put("password", "passw0rd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andDo(MockMvcResultHandlers.print());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/erin.z"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"first_name\":\"erin\",\"last_name\":\"zhang\",\"email\":\"erinzhang@husky.neu.edu\",\"username\":\"erin.z\",\"enabled\":true,\"roles\":[],\"reviews\":[],\"friends\":{\"id\":1,\"request\":[],\"friends\":[]}}"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/someRandomUser"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void login() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "erinzhang@husky.neu.edu");
        request.put("password", "passw0rd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
