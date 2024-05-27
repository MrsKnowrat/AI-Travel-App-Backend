package com.example.travelactivityapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "Email is mandatory")
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    @Email(message = "Email must be valid")
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 8 characters long") //Don't need JsonIgnore here because it is already in User
    private String password;
}


