package edu.northeastern.cs4500.spoiledTomatillos.setup;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import edu.northeastern.cs4500.spoiledTomatillos.SpoiledTomatillosApplication;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpoiledTomatillosApplication.class);
	}

}
