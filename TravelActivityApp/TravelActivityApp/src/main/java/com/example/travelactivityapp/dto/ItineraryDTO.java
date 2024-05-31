package com.example.travelactivityapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryDTO {

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

    private String username;

    private List<ActivityDTO> activities;
    private List<TagDTO> tags;
}
