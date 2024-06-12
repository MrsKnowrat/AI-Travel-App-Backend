package com.example.travelactivityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class} ) // will disable all in dependency except for what we call and use
public class TravelActivityAppApplication {

    public static void main(String[] args) { // This is the main method that runs the application
        SpringApplication.run(TravelActivityAppApplication.class, args); // This runs the Spring Boot application
    }
}

/* This is the main class for the Travel Activity App. It is the entry point for the application. */

