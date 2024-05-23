package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer tagId;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Itinerary> itineraries = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Activity> activities = new HashSet<>();

}
