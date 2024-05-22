package com.example.travelactivityapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Itinerary> itineraries = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Activity> activities = new HashSet<>();

}
