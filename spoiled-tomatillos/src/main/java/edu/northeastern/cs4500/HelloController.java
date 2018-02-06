package edu.northeastern.cs4500;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/api/hello/string") 
	public String hello() {
		return "Hello World!";
	}
}
