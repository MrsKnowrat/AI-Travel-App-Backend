package com.example.travelactivityapp.model;

// This class represents the Profile entity

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity // This class is an entity  
@Data // Lombok annotation to generate getters and setters
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
@Builder // Lombok annotation to generate a builder
public class Profile {

    @Id // This field is the primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // This field is generated by the database
    private Long id; // This field is the id

    @Column(name = "firstName", nullable = false, length = 50)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "dateOfBirth", nullable = false, length = 10)
    @NotNull(message = "Date of birth is required in this format: yyyy-mm-dd")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirth;

    // non-required fields
    private String bio;
    private String profilePicture;
    private String preferences;

    @OneToOne(mappedBy = "profile")
    @JsonIgnore // added to avoid infinite loop between user and profile when registering a new user and creating a profile
    private User user; // This defines relationship between the User and Profile entities in relational DB managed by JPA.
}