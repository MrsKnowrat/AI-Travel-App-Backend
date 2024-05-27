package com.example.travelactivityapp.config;

// in order to Autowire the Model Mapper, we needed to create a bean of the Model Mapper here.

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
