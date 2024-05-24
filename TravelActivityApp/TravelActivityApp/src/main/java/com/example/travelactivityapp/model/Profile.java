package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate dateOfBirth;

    private String bio;
    private String profilePicture;
    private String preferences;

    @OneToOne(mappedBy = "profile")
    @JoinColumn(name = "user_id")
    private User user; // This defines relationship between the User and Profile entities in relational DB managed by JPA.
}
