package com.example.travelactivityapp.controller;

// This class handles HTTP requests for itineraries 

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.ItineraryDTO;
import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.service.ItineraryService;
import com.example.travelactivityapp.util.ModelMapperUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Enables logging within the class
@RestController // Marks class as controller; methods return domain object
@Validated // Ensures beans are validated before processing
@CrossOrigin(origins = "*") // Allows c/o requests from all domains
@RequestMapping("/itineraries") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class ItineraryController {

    @Autowired // Handles business logic for itinerary-related ops
    ItineraryService itineraryService;

    @Autowired // For object mapping
    ModelMapperUtil modelMapperUtil;

    // Create Itinerary
    @PostMapping
    public ResponseEntity<?> createItinerary(@Valid @RequestBody ItineraryDTO itineraryDTO) { // Returns response entity with created itinerary
        System.out.println("I've been activated");
        Itinerary itinerary = itineraryService.createItineraryForUser(itineraryDTO); // Creates itinerary for user
        CommonResponse response = CommonResponse.builder().hasError(false).data(itinerary).message("Itinerary created successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    // Receives itinerary data and API key, then calls processApiResponse from service
    @PostMapping("/processApiResponse")
    public ResponseEntity<?> processApiResponse(@Valid @RequestBody ItineraryDTO itineraryDTO, @RequestParam String apiKey) { // Returns response entity with processed API response
        try {
            itineraryService.processApiResponse(itineraryDTO.toString(), apiKey); // Processes API response 
            return ResponseEntity.ok(new CommonResponse(null, "Activities and tags processed successfully", null, HttpStatus.OK, false, null)); // Returns response entity with common response
        } catch (Exception e) { // If exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(null, "Failed to process activities and tags", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, true, null));
        }
    }

    // Update/Save Itinerary
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItinerary(@Valid @PathVariable Long id, @RequestBody ItineraryDTO itineraryDetails) { // Returns response entity with updated itinerary
        try {
            Itinerary itinerary = modelMapperUtil.map(itineraryDetails, Itinerary.class); // Maps itinerary details to itinerary
            Itinerary updatedItinerary = itineraryService.updateItinerary(itinerary, id); // Updates itinerary
            CommonResponse response = CommonResponse.builder().hasError(false).data(updatedItinerary).message("Itinerary Updated Successfully").status(HttpStatus.OK).build(); // Builds common response
            return ResponseEntity.ok(response); // Returns response entity with response
        } catch (Exception e) { // If exception occurs
            CommonResponse response = CommonResponse.builder().hasError(true).message("Itinerary failed to update").status(HttpStatus.BAD_REQUEST).build(); // Builds common response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Returns response entity with response
        }
    }

    // Get Itinerary By Username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllItinerariesByUsername(@Valid @PathVariable String username) { // Returns response entity with list of itineraries by username
        List<ItineraryDTO> itineraryDTOs = itineraryService.getItinerariesByUsername(username); // Gets itineraries by username
        CommonResponse response = CommonResponse.builder().hasError(false).data(itineraryDTOs).message("Itinerary fetched successfully").status(HttpStatus.OK).build();

        return ResponseEntity.ok(response); // Returns response entity with response
    }
}
    // Delete Itinerary - coming soon
