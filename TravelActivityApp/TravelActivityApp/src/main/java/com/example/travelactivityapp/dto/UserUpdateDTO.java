package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the UserUpdate entity. 
It is used to transfer data between the client and the server. */

import com.example.travelactivityapp.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@Builder // This is a Lombok annotation that generates a builder class for the class
public class UserUpdateDTO {


    @NotBlank(message = "Username is required") // This is a validation annotation that checks if the username field is not blank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") // This is a validation annotation that checks if the username field is between 3 and 50 characters long
    @Column(name = "username", nullable = false, length = 50, unique = true) // This is a JPA annotation that maps the username field to the "username" column in the database
    private String username; // This is the username of the user

    @NotBlank(message = "Email address is required") 
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters") // checks email address to have between 3 and 50 characters
    @Email(message = "Email address should be valid") // checks email address to have an @ and .
    private String email; 

    @NotBlank(message = "Password is required") 
    @Size(min = 8, message = "Password must be at least 8 characters long") 
    @Column(name = "password", nullable = false, length = 255) // checks password to have at least 8 characters
    @JsonIgnore // ensures that the password will not console log during login
    private String password;

    @NotNull // checks address to have all fields
    private Address address; 

    @NotBlank (message = "Old password is required") //when user wants to update password, must provide old password for verification
    private String oldPassword;

    @NotBlank(message = "New password is required") 
    @Size(min = 6, message = "New password must be at leat 6 characters long") // checks new password to have at least 6 characters
    private String newPassword; 

}
