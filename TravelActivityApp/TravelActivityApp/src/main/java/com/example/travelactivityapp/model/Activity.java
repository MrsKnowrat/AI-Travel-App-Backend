package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer activityId;

    private String activityName;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany(mappedBy = "activities")
    private Set<Itinerary> itineraries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "activity_tag",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

}
