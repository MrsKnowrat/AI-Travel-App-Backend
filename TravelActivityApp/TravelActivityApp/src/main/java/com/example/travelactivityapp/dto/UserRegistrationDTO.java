package com.example.travelactivityapp.dto;

import com.example.travelactivityapp.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @NotBlank(message = "Email address is required") // checks email address to have an @ and .
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    @Column(name = "email", nullable = false, length = 50, unique = true)
    @Email(message = "Email address should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false, length = 255) // since JsonIgnore is in User class, we don't need it here
    private String password;

    @Valid
    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;

}
