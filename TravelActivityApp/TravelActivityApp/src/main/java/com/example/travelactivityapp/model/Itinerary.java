package com.example.travelactivityapp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Itinerary {

        // TODO 4
        // add more things to your itinerary DTO/model like travel preferences (eg. family with 2 adults 2 kids or whatever)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "itineraryName", nullable = false, columnDefinition = "TEXT")
    private String itineraryName;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "Start Date", nullable = false)
    private LocalDate startDate;

    @Column(name = "End Date", nullable = false)
    private LocalDate endDate;

    @JsonBackReference // prevent infinite reference loop
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = false)
    private User user;

    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "itinerary_tag",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "itinerary_activity",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities = new HashSet<>();

    //Set is used instead of List for faster lookup times and it does not allow duplicate elements
}
