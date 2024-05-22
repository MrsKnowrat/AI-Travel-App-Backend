package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer profileId;
    private String bio;
    private String profilePicture;
    private String preferences;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "profile")
    private User user; // This defines relationship between the User and Profile entities in relational DB managed by JPA.
}
