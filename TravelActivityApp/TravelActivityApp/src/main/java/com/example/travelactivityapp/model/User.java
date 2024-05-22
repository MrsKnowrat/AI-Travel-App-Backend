package com.example.travelactivityapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    @NotBlank(message = "Username is required")
    private String username;

    @JsonIgnore // ensures that the password will not console log during login
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email address is required")
    @Email(message = "Email address should be valid")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL) // 1:1 relationship with Profile class.
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries = new ArrayList<>();

}
