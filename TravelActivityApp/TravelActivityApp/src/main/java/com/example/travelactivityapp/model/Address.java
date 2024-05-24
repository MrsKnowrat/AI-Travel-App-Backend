package com.example.travelactivityapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {

    @NotBlank(message = "Street address is required")
    @Size(max = 100, message = "Street address must be less than 100 characters")
    @Column(name = "street", nullable = false, length = 100)
    private String street;

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
    @Pattern(regexp = "\\d{5}(-\\d{4})?")
    @Column(name = "zipCode", nullable = false, length = 20)
    private String zipCode;
}
