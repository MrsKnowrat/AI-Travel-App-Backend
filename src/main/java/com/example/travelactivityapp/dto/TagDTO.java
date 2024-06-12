package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the Tag entity. 
It is used to transfer data between the client and the server. */

import lombok.AllArgsConstructor; // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
import lombok.Builder; // This is a Lombok annotation that generates a builder class for the class
import lombok.Data; // This is a Lombok annotation that generates getters, setters, and other methods for the class
import lombok.NoArgsConstructor; // This is a Lombok annotation that generates a no-args constructor for the class

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@Builder // This is a Lombok annotation that generates a builder class for the class
public class TagDTO {

    private String tagName; 
}
