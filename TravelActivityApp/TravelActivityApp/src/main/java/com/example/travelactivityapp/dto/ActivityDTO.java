package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the Activity entity. 
It is used to transfer data between the client and the server. It 
also holds the structure of the JSON response. */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class   
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@Builder // This is a Lombok annotation that generates a builder class for the class
public class ActivityDTO {

    private String activityName; // This is the name of the activity

    private List<TagDTO> tags; // This is a list of tags for the activity
}