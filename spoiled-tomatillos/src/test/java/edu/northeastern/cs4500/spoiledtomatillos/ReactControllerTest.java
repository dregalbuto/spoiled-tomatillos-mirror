package edu.northeastern.cs4500.spoiledtomatillos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test if forwarding is successful.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void movie() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/Movie/tt0000001"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void bundle() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/built/bundle.js"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}