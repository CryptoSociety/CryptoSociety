package com.codeup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CryptoSocietyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CryptoSocietyApplication.class, args);
	}

	protected SpringApplicationBuilder configure(org.springframework.boot.builder.SpringApplicationBuilder application){
		return application.sources(CryptoSocietyApplication.class);
	}
}
