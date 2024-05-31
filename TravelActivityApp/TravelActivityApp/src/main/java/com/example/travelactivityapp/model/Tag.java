package com.example.travelactivityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Itinerary> itineraries = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

}
