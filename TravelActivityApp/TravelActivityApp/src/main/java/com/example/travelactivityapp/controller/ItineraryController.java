package com.example.travelactivityapp.controller;

/* This class handles  */

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
    public ResponseEntity<?> createItinerary(@Valid @RequestBody ItineraryDTO itineraryDTO) {
        System.out.println("I've been activated");
        Itinerary itinerary = itineraryService.createItineraryForUser(itineraryDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(itinerary).message("Itinerary created successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    // Receives itinerary data and API key, then calls processApiResponse from service
    @PostMapping("/processApiResponse")
    public ResponseEntity<?> processApiResponse(@Valid @RequestBody ItineraryDTO itineraryDTO, @RequestParam String apiKey) {
        try {
            itineraryService.processApiResponse(itineraryDTO.toString(), apiKey);
            return ResponseEntity.ok(new CommonResponse(null, "Activities and tags processed successfully", null, HttpStatus.OK, false, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(null, "Failed to process activities and tags", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, true, null));
        }
    }

    // Update/Save Itinerary
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItinerary(@Valid @PathVariable Long id, @RequestBody ItineraryDTO itineraryDetails) {
        try {
            Itinerary itinerary = modelMapperUtil.map(itineraryDetails, Itinerary.class);
            Itinerary updatedItinerary = itineraryService.updateItinerary(itinerary, id);
            CommonResponse response = CommonResponse.builder().hasError(false).data(updatedItinerary).message("Itinerary Updated Successfully").status(HttpStatus.OK).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
                    CommonResponse response = CommonResponse.builder().hasError(true).message("Itinerary failed to update").status(HttpStatus.BAD_REQUEST).build();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Get Itinerary By Username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllItinerariesByUsername(@Valid @PathVariable String username) {
        List<ItineraryDTO> itineraryDTOs = itineraryService.getItinerariesByUsername(username);
        CommonResponse response = CommonResponse.builder().hasError(false).data(itineraryDTOs).message("Itinerary fetched successfully").status(HttpStatus.OK).build();

        return ResponseEntity.ok(response);
    }
}

    // Delete Itinerary - coming soon

    // Get Itinerary By User ID 
    // @GetMapping("/user/{userId}")
    // public List<Itinerary> getUserItinerariesByUserId(Long id) {
    // List<Itinerary> itineraries =
    // itineraryService.getUserItinerariesByUserId(id);
    // if (itineraries.isEmpty()) {
    // throw new RuntimeException("No itineraries found for the user with ID: " +
    // id);
    // }
    // return itineraries;
    // }