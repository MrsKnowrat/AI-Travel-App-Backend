package com.example.travelactivityapp.dto;

// Holds structure of the JSON response.

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private String tagName;
}
