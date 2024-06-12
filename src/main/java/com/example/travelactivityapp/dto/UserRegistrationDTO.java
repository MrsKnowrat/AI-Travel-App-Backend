package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the UserRegistration entity. 
It is used to transfer data between the client and the server. */

import com.example.travelactivityapp.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@Builder // This is a Lombok annotation that generates a builder class for the class
public class UserRegistrationDTO {

    @NotBlank(message = "Username is required") // This is a validation annotation that checks if the username field is not blank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") // This is a validation annotation that checks if the username field is between 3 and 50 characters long
    @Column(name = "username", nullable = false, length = 50, unique = true) // This is a JPA annotation that maps the username field to the "username" column in the database
    private String username; 

    @NotBlank(message = "Email address is required") 
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters") 
    @Column(name = "email", nullable = false, length = 50, unique = true) // checks email address to have between 3 and 50 characters
    @Email(message = "Email address should be valid") // checks email address to have an @ and .
    private String email; 

    @NotBlank(message = "Password is required") 
    @Size(min = 8, message = "Password must be at least 8 characters long") // checks password to have at least 8 characters
    @Column(name = "password", nullable = false, length = 255) 
    private String password; 

    @NotBlank(message = "First name is required") // checks first name to have at least 3 characters
    @Column(name = "firstName", nullable = false, length = 50) // checks first name to have between 3 and 50 characters
    private String firstName; 

    @NotBlank(message = "Last name is required") // checks last name to have at least 3 characters
    @Column(name = "lastName", nullable = false, length = 50) // checks last name to have between 3 and 50 characters
    private String lastName; 

    @NotNull(message = "Date of birth is required in this format: yyyy/mm/dd\"") 
    @Column(name = "dateOfBirth", nullable = false, length = 10) 
    @DateTimeFormat(pattern = "yyyy-mm-dd") // checks date of birth to have the format yyyy/mm/dd
    private LocalDate dateOfBirth; 

    @Valid // Ensures the Address object adheres to its class-defined constraints.
    @Embedded
    @Column(name = "address", nullable = false) 
    private Address address; 
}

