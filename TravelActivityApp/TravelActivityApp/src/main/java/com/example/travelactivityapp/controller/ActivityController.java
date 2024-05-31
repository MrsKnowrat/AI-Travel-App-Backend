package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.service.ActivityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Enables logging within the class
@RestController // Marks class as controller; methods return domain object
@Validated // Ensures beans are validated before processing
@CrossOrigin(origins = "*") // Allows c/o requests from all domains
@RequestMapping("/activities") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class ActivityController {

    @Autowired // Handles business logic for activity-related ops
    ActivityService activityService;

    // Create Activity
    @PostMapping // Maps HTTP POST requests to this method
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) { // Returns response entity with created activity
        return ResponseEntity.ok(activityService.saveActivity(activity)); // Saves activity
    }

    // Get all activities
    @GetMapping // Maps HTTP GET requests to this method
    public ResponseEntity<List<Activity>> getAllActivities() { // Returns response entity with list of all activities
        return ResponseEntity.ok(activityService.getAllActivities()); // Gets all activities
    }

    // Update Activity 
    @PutMapping("/{id}") // Maps HTTP PUT requests to this method
    public ResponseEntity<Activity> updateActivity(@Valid @PathVariable Long id, @RequestBody Activity activityDetails) { // Returns response entity with updated activity
        return ResponseEntity.ok(activityService.updateActivity(id, activityDetails)); // Updates activity
    }

    // Delete Activity
    @DeleteMapping("/{id}") // Maps HTTP DELETE requests to this method
    public ResponseEntity<Void> deleteActivity(@Valid @PathVariable Long id) { // Returns response entity with no content
        activityService.deleteActivity(id); // Deletes activity
        return ResponseEntity.noContent().build(); // Returns response entity with no content
    }
}
