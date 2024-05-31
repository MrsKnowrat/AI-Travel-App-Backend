package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String activityName;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // causes infinite recursion, not sure why
    // @Builder.Default
    // @ManyToMany(mappedBy = "activities", fetch = FetchType.LAZY)  // Specify lazy fetching
    // @JsonIgnore
    // private Set<Itinerary> itineraries = new HashSet<>();

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "itinerary_id")
    // private Itinerary itinerary;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
        name = "activity_tag",
        joinColumns = @JoinColumn(name = "activity_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

}
