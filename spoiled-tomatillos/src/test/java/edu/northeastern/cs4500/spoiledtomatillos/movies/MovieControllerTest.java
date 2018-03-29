package edu.northeastern.cs4500.spoiledtomatillos.movies;

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

import static org.junit.Assert.*;

/**
 * Test the external facing api for movie.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void info() throws Exception {
        String ret = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/info?id=tt0000001"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(ret.startsWith("{\"id\":\"tt0000001\",\"titleType\":\"movie\"," +
                "\"title\":\"Carmencita\",\"releaseDate\":\"10 Mar 1894\"," +
                "\"genres\":\"Documentary, Short\",\"actors\":\"Carmencita\"," +
                "\"description\":\"Performing on what looks like a small wooden stage, " +
                "wearing a dress with a hoop skirt and white high-heeled pumps, Carmencita " +
                "does a dance with kicks and twirls, a smile always on her face.\"," +
                "\"runtimeMinuets\":\"1 min\"," +
                "\"imgURL\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMjAzNDEwMzk3OV5B" +
                "Ml5BanBnXkFtZTcwOTk4OTM5Ng@@._V1_SX300.jpg\",\"source\":\"OMDB\",\"reviews\":["));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/info?id=asd"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/info?id=tt0000001"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/search?s=mencit"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[\"tt0000001\"]"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/search?s=mencitasd"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}