package edu.northeastern.cs4500;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;

import edu.northeastern.cs4500.config.MvcConfig;


@SpringBootApplication
public class SpoiledTomatillosApplication {

	private static TemplateEngine templateEngine;
		
	public static void main(String[] args) {
		templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new MvcConfig().templateResolver());
		SpringApplication.run(SpoiledTomatillosApplication.class, args);
	}
}
