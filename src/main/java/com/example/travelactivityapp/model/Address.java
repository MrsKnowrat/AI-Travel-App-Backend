package com.example.travelactivityapp.model;

// This class represents the Address entity 

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getters and setters
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
@Builder // Lombok annotation to generate a builder
@Embeddable // This class is embedded in other entities
public class Address {

    @NotBlank(message = "Street address is required") // This field is required
    @Size(max = 100, message = "Street address must be less than 100 characters") // This field must be less than 100 characters
    @Column(name = "street", nullable = false, length = 100) // This field is the street name
    private String street; // This field is the street name

    @NotBlank(message = "City is required") 
    @Size(max = 50, message = "City name must be less than 50 characters") 
    @Column(name = "city", nullable = false, length = 50) 
    private String city;

    @NotBlank(message = "State or Province is required")
    @Size(max = 50, message = "State or Province name must be less than 50 characters")
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @NotBlank(message = "Zip Code is required") 
    @Size(max = 20, message = "Zip code must be less than 20 characters") 
    @Pattern(regexp = "\\d{5}(-\\d{4})?") // This field must be a valid US zip code 
    @Column(name = "zipCode", nullable = false, length = 20) 
    private String zipCode; 
}
