package com.example.travelactivityapp.config;

import com.example.travelactivityapp.aop.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

   // @Bean - this was commented out to resolve a circular reference and remove dependency cycle between beans
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}