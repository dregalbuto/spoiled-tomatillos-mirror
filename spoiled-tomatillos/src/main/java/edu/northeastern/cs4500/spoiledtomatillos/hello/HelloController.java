package edu.northeastern.cs4500.spoiledtomatillos.hello;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloController {
	@Autowired
	HelloRepository helloRepository;
	
	@RequestMapping("/hello/insert")
	public HelloObject insertHelloObject() {
		HelloObject obj = new HelloObject("Hello!");
		helloRepository.save(obj);
		return obj;
	}
	
	@RequestMapping("/hello/select/all")
	public List<HelloObject> selectAllHelloObjects() {
		List<HelloObject> hellos =
		(List<HelloObject>)helloRepository.findAll();
		return hellos;
	}
	
	@RequestMapping("/hello/insert/{msg}")
	public HelloObject insertMessage(@PathVariable("msg") String message)
	{
		HelloObject obj = new HelloObject(message);
		helloRepository.save(obj);
		return obj;
	}
	
	@RequestMapping("/hello/string") 
	public String hello() {
		return "Hello World!";
	}
	
	@RequestMapping("/hello/object") 
	public HelloObject helloObject() {
		HelloObject obj = new HelloObject("Hello World!");
		return obj;
	}
}
