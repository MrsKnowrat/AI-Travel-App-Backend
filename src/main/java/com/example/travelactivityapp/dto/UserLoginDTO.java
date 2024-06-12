package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the UserLogin entity. 
It is used to transfer data between the client and the server. */

import jakarta.persistence.Column; 
import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.Size; 
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
public class UserLoginDTO {

    @NotBlank(message = "Email is mandatory") // This is a validation annotation that checks if the email field is not blank
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters") // This is a validation annotation that checks if the email field is between 3 and 50 characters long
    @Email(message = "Email must be valid") // This is a validation annotation that checks if the email field is valid
    @Column(name = "email", nullable = false, length = 50, unique = true) // This is a JPA annotation that maps the email field to the "email" column in the database
    private String email; 

    @NotBlank(message = "Password is mandatory") // This is a validation annotation that checks if the password field is not blank
    @Size(min = 6, message = "Password must be at least 8 characters long") // Don't need JsonIgnore here because it is already in User
    private String password; 
}