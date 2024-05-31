package com.example.travelactivityapp.dto;

/* This class is a DTO that is used to return a common response to the client. */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@Builder 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private Object data; // This is the data that will be returned to the client
    private String message; // i.e. user created
    private String additionalDetails; // This is additional details that will be returned to the client
    private HttpStatus status;  
    private Boolean hasError; //if there is an error...
    private String error; //...then get the error
}

