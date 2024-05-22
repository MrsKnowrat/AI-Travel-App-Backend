package com.example.travelactivityapp.model;

import jakarta.persistence.*;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bio;
    private String profilePicture;
    private String preferences;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "profile")
    private User user; // This defines relationship between the User and Profile entities in relational DB managed by JPA.
}
