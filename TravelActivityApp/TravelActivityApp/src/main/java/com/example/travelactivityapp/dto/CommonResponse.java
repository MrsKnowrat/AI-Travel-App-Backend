package com.example.travelactivityapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private Object data;
    private String message; // i.e. user created
    private String additionalDetails;
    private HttpStatus status;
    private Boolean hasError; //if there is an error...
    private String error; //...then get the error
}

