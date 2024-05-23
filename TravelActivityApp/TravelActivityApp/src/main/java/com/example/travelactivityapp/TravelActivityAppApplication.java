package com.example.travelactivityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.travelactivityapp.model"}) // include this?
@EnableJpaRepositories(basePackages = {"com.example.travelactivityapp.repository"}) //include this?
public class TravelActivityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelActivityAppApplication.class, args);
    }

}
