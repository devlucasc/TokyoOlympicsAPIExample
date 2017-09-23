package com.tokyo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.tokyo.api"})
public class Application {

    /**
     * Main Method - Start Application
     * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
