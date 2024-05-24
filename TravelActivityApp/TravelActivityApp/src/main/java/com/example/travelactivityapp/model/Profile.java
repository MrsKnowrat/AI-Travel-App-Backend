package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String bio;
    private String profilePicture;
    private String preferences;

    @OneToOne(mappedBy = "profile")
    @JoinColumn(name = "user_id")
    private User user; // This defines relationship between the User and Profile entities in relational DB managed by JPA.
}
