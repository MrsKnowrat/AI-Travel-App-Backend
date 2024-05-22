package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.service.ItineraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/itineraries")
public class ItineraryController {
    @Autowired
    private ItineraryService itineraryService;

    @PostMapping("/save")
    public ResponseEntity<Itinerary> saveItinerary(@RequestBody Itinerary itinerary, @RequestParam Integer userId) {
        Itinerary savedItinerary = itineraryService.saveItinerary(itinerary, userId);
        return ResponseEntity.ok(savedItinerary);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Itinerary>> getUserItineraries(@PathVariable Integer userId) {
        List<Itinerary> itineraries = itineraryService.getUserItineraries(userId);
        return ResponseEntity.ok(itineraries);
    }

}
