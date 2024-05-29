package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.ItineraryDTO;
import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.service.ItineraryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/itineraries")
@Validated
@CrossOrigin(origins = "*")
public class ItineraryController {

    @Autowired
    ItineraryService itineraryService;

    /* CRUD
    C- Included here
    R- Included here
    U- Included here
    D- Included here
    */

    // Create Itinerary
    @PostMapping
    public ResponseEntity<?> createItinerary(@Valid @RequestBody ItineraryDTO itineraryDTO) {
        Itinerary itinerary = itineraryService.createItineraryForUser(itineraryDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(itinerary).message("Itinerary created successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    // Get Itinerary By Username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllItinerariesByUsername(@Valid @PathVariable String username) {
        List<Itinerary> itineraries = itineraryService.getAllItinerariesByUser(username);
        CommonResponse response = CommonResponse.builder().hasError(false).data(itineraries).message("Itinerary fetched successfully").status(HttpStatus.OK).build();

        return ResponseEntity.ok(response);
    }

    // Get Itinerary By User ID - delete this
//    @GetMapping("/user/{userId}")
//    public List<Itinerary> getUserItinerariesByUserId(Long id) {
//        List<Itinerary> itineraries = itineraryService.getUserItinerariesByUserId(id);
//        if (itineraries.isEmpty()) {
//            throw new RuntimeException("No itineraries found for the user with ID: " + id);
//        }
//        return itineraries;
//    }

    // Update/Save Itinerary
    public ResponseEntity<?> saveItinerary(@RequestBody Itinerary itinerary, @RequestParam Long id) {
        try {
            Itinerary savedItinerary = itineraryService.saveItinerary(itinerary, id);
            CommonResponse response = CommonResponse.builder()
                    .hasError(false)
                    .data(savedItinerary)
                    .message("Itinerary saved successfully")
                    .status(HttpStatus.OK)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CommonResponse response = CommonResponse.builder()
                    .hasError(true)
                    .message("Error saving itinerary: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Delete Itinerary

}
