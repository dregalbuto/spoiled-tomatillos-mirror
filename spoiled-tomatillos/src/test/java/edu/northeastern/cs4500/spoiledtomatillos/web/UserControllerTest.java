package edu.northeastern.cs4500.spoiledtomatillos.web;

import static org.junit.Assert.assertEquals;

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

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

	@Test
	public void getUserById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/id/1000009"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getUserByEmail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/email/moe@husky.neu.edu"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
    @Test
    public void registerAndInfoAndLogin() throws Exception {
        JSONObject request = new JSONObject();
        request.put("first_name", "erin");
        request.put("last_name", "zhang");
        request.put("email", "erinzhang@husky.neu.edu");
        request.put("username", "erin.z");
        request.put("password", "passw0rd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andDo(MockMvcResultHandlers.print());
        String str = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/erin.z"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(str.matches("\\{\"id\"\\:[0-9]+,\"first_name\"\\:\"erin\",\"last_name\"\\:\"zhang\"," +
                "\"email\"\\:\"erinzhang@husky.neu.edu\",\"username\"\\:\"erin.z\"," +
                "\"enabled\"\\:true,\"roles\"\\:\\[\\],\"reviews\"\\:\\[\\]," +
                "\"friends\"\\:\\{\"id\"\\:[0-9]*,\"requests\"\\:\\[\\],\"friends\"\\:\\[\\]\\}," +
                "\"groups\"\\:\\[\\]\\}"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/someRandomUser"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));


        JSONObject loginRe = new JSONObject();
        loginRe.put("email", "erinzhang@husky.neu.edu");
        loginRe.put("password", "passw0rd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(loginRe.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void signup(JSONObject per) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()));
    }

    private String login(JSONObject per) throws Exception {
        String cont = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(per.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return new JSONObject(cont).getString("token");
    }

    private String signupLogin(JSONObject per) throws Exception {
        this.signup(per);
        return this.login(per);
    }

    @Test
    public void loginAdminAndPromote() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "tomatillosspoiled@gmail.com");
        request.put("password", "admin");
        String token  = new JSONObject(
                this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString()).getString("token");

        JSONObject erin = new JSONObject();
        erin.put("first_name", "erin");
        erin.put("last_name", "zhang");
        erin.put("email", "erinzhang@husky.neu.edu");
        erin.put("username", "erin.z");
        erin.put("password", "passw0rd");
        this.signupLogin(erin);

        String str = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/erin.z"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(str.matches("\\{\"id\"\\:[0-9]+,\"first_name\"\\:\"erin\",\"last_name\"\\:\"zhang\"," +
                "\"email\"\\:\"erinzhang@husky.neu.edu\",\"username\"\\:\"erin.z\"," +
                "\"enabled\"\\:true,\"roles\"\\:\\[\\],\"reviews\"\\:\\[\\]," +
                "\"friends\"\\:\\{\"id\"\\:[0-9]+,\"requests\"\\:\\[\\],\"friends\"\\:\\[\\]\\}," +
                "\"groups\"\\:\\[\\]\\}"));

        JSONObject promotion = new JSONObject();
        promotion.put("email", "tomatillosspoiled@gmail.com");
        promotion.put("token", token);
        promotion.put("targetEmail", "erinzhang@husky.neu.edu");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/promote")
                .contentType(MediaType.APPLICATION_JSON).content(promotion.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        str = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/username/erin.z"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(str.matches("\\{\"id\"\\:[0-9]+,\"first_name\"\\:\"erin\",\"last_name\"\\:\"zhang\"," +
                "\"email\"\\:\"erinzhang@husky.neu.edu\",\"username\"\\:\"erin.z\"," +
                "\"enabled\"\\:true,\"roles\"\\:\\[\\{\"id\"\\:[0-9]+,\"name\"\\:\"ROLE_ADMIN\"," +
                "\"privileges\"\\:\\[\\]\\}\\],\"reviews\"\\:\\[\\]," +
                "\"friends\"\\:\\{\"id\"\\:[0-9]*,\"requests\"\\:\\[\\],\"friends\"\\:\\[\\]\\}," +
                "\"groups\"\\:\\[\\]\\}"));
    }

    @Test
    public void resetAndForgetPassword() throws Exception {
        JSONObject erin = new JSONObject();
        erin.put("first_name", "erin");
        erin.put("last_name", "zhang");
        erin.put("email", "erinzhang@husky.neu.edu");
        erin.put("username", "erin.z");
        erin.put("password", "passw0rd");
        this.signupLogin(erin);

        erin.put("newPassword", "alternate");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/change")
                .contentType(MediaType.APPLICATION_JSON).content(erin.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();


        JSONObject erinNew = new JSONObject();
        erinNew.put("email", "erinzhang@husky.neu.edu");
        erinNew.put("password", "alternate");
        String token = this.login(erinNew);

        JSONObject nobody = new JSONObject();
        nobody.put("email", "nobody@nobody.neu.edu");
        nobody.put("first_name", "erin");
        nobody.put("last_name", "zhang");
        nobody.put("username", "nobody");
        nobody.put("password", "passw0rd");
        this.signupLogin(nobody);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/forget")
                .contentType(MediaType.APPLICATION_JSON).content(nobody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        erinNew.put("newPassword", "passw0rd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/change")
                .contentType(MediaType.APPLICATION_JSON).content(erinNew.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    
    @Test
    public void loginInvalid() throws Exception {
        JSONObject request = new JSONObject();
        request.put(JsonStrings.EMAIL, "somethingRandom");
        JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.USER_NOT_FOUND, response.getString(JsonStrings.MESSAGE));
    }
    
    @Test
    public void loginBadPassword() throws Exception {
        JSONObject request = new JSONObject();
        request.put(JsonStrings.EMAIL, "erinzhang@husky.neu.edu");
        request.put(JsonStrings.SECRET, "SECRET");
        JSONObject response = new JSONObject(this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(request.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString());
        assertEquals(JsonStrings.BAD_SECRET, response.getString(JsonStrings.MESSAGE));
    }
    
}
