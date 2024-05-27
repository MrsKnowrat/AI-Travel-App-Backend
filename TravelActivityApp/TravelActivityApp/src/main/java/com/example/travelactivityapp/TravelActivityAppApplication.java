package com.example.travelactivityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class} ) // will disable all in dependency except for what we call and use
public class TravelActivityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelActivityAppApplication.class, args);
    }

}
