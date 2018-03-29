package edu.northeastern.cs4500.spoiledtomatillos;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class Helper {
	public static String signupLogin(JSONObject per, MockMvc mockMvc) throws Exception {
        // Signup
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()))
                .andDo(MockMvcResultHandlers.print());

        // Login
        String cont = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return new JSONObject(cont).getString("token");

    }
	
	public static String signupLoginDefaults(String email, MockMvc mockMvc) throws Exception {
		JSONObject request = new JSONObject();
		request.put("first_name", "test_gf");
		request.put("last_name", "test_gl");
		request.put("email", email);
		request.put("username", "test_gu");
		request.put("password", "passw0rd");
		return Helper.signupLogin(request, mockMvc);
	}
}
