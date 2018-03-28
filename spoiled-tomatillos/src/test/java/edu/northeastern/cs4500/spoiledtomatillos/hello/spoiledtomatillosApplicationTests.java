package edu.northeastern.cs4500.spoiledtomatillos.hello;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class spoiledtomatillosApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testHelloObject() {
		HelloObject o = new HelloObject("Test");
		assertEquals(o.getMessage(), "Test");
		o.setMessage("New message");
		assertEquals(o.getMessage(), "New message");
	}
	
}
