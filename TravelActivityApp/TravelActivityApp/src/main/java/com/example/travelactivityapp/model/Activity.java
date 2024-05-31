package com.example.travelactivityapp.model;

/* This class represents the Activity entity */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity // This class is an entity
@Data // Lombok annotation to generate getters and setters
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
@Builder // Lombok annotation to generate a builder
public class Activity {
    @Id // This field is the primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // This field is generated by the database
    private Long id; // This field is the primary key

    private String activityName; 
    private String description; 
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // These relationships had to be commented out as it causes infinite recursion, not sure why
    // @Builder.Default
    // @ManyToMany(mappedBy = "activities", fetch = FetchType.LAZY)  // Specify lazy fetching
    // @JsonIgnore
    // private Set<Itinerary> itineraries = new HashSet<>();

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "itinerary_id")
    // private Itinerary itinerary;

    @Builder.Default // Lombok annotation to generate a default builder
    @ManyToMany(fetch = FetchType.LAZY) // This field is a many-to-many relationship
    @JsonIgnore // This field is ignored in the JSON response
    @JoinTable( // This field is a join table
        name = "activity_tag", // This field is the name of the join table
        joinColumns = @JoinColumn(name = "activity_id"), // This field is the join column
        inverseJoinColumns = @JoinColumn(name = "tag_id") // This field is the inverse join column
    )
    private Set<Tag> tags = new HashSet<>(); // This field is a set of tags
}
