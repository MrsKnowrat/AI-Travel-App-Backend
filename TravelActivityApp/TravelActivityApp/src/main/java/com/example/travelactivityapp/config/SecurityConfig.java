package com.example.travelactivityapp.config;

/* This class is a configuration class in Spring Boot to set up security-related beans.
* Specifically, this class defines a bean for PasswordEncoder using the BCryptEncoder
* implementation.  */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}