package com.example.travelactivityapp.dto;

// Holds structure of the JSON response.

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDTO {

    private String activityName;

    private List<TagDTO> tags;
}