package com.example.travelactivityapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @JsonIgnore // ensures that the password will not console log during login
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL) // 1:1 relationship with Profile class.
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries;

}
