package com.example.travelactivityapp.dto;

/* This class is a Data Transfer Object (DTO) for the Itinerary entity. 
It is used to transfer data between the client and the server. */

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDate;

@Data // This is a Lombok annotation that generates getters, setters, and other methods for the class
@NoArgsConstructor // This is a Lombok annotation that generates a no-args constructor for the class
@AllArgsConstructor // This is a Lombok annotation that generates a constructor with all the fields as arguments for the class
@Builder // This is a Lombok annotation that generates a builder class for the class
public class ItineraryDTO {

    @NotBlank(message = "Title is required") // This is a validation annotation that checks if the itineraryName field is not blank
    @Column(name = "itineraryName", nullable = false, columnDefinition = "TEXT") // This is a JPA annotation that maps the itineraryName field to the "itineraryName" column in the database
    private String itineraryName; 

    @NotBlank(message = "Description is required") // This is a validation annotation that checks if the description field is not blank
    @Column(name = "description", nullable = false, columnDefinition = "TEXT") // This is a JPA annotation that maps the description field to the "description" column in the database
    private String description; // This is the description of the itinerary

    @Column(name = "Start Date", nullable = false) // This is a JPA annotation that maps the startDate field to the "Start Date" column in the database
    private LocalDate startDate; // This is the start date of the itinerary

    @Column(name = "End Date", nullable = false) // This is a JPA annotation that maps the endDate field to the "End Date" column in the database
    private LocalDate endDate; // This is the end date of the itinerary

    private String username; // This is the username of the user who created the itinerary

    private List<ActivityDTO> activities; // This is a list of activities for the itinerary
    private List<TagDTO> tags; // This is a list of tags for the itinerary
}
