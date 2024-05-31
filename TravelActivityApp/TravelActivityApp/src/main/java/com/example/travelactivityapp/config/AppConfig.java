package com.example.travelactivityapp.config;

/* This class is a configuration class in Spring Boot to define beans managed
* by the Spring container. Here, a bean for Model Mapper is defined, which is a
* library that simplifies object mapping to different types. In this case, used to
* map between entity models and DTOs.  */

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Indicator that this class contains Spring bean definitions
public class AppConfig {

    // MM bean instantiates & returns new MM object, which can be injected in other components in the app.
    @Bean // Tells Spring that a method will return an object that should be registered as a bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
