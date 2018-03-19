package edu.northeastern.cs4500.spoiledtomatillos.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Test
  public void sendFriendRequest() throws Exception {
  }

  @Test
  public void acceptFriendRequest() throws Exception {
  }

  @Test
  public void rejectFriendRequest() throws Exception {
  }

  @Test
  public void unfriend() throws Exception {
  }

  @Test
  public void list() throws Exception {
  }

  @Test
  public void request() throws Exception {
  }

}