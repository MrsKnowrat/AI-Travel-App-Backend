package com.example.travelactivityapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
