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
    private ItineraryService itineraryService;

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

    @PostMapping("/save")
    public ResponseEntity<Itinerary> saveItinerary(@RequestBody Itinerary itinerary, @RequestParam Long id) {
        Itinerary savedItinerary = itineraryService.saveItinerary(itinerary, id);
        return ResponseEntity.ok(savedItinerary);
    }

    @GetMapping("/user/{userId}")
    public List<Itinerary> getUserItinerariesByUserId(Long id) {
        List<Itinerary> itineraries = itineraryService.getUserItinerariesByUserId(id);
        if (itineraries.isEmpty()) {
            throw new RuntimeException("No itineraries found for the user with ID: " + id);
        }
        return itineraries;
    }
}
