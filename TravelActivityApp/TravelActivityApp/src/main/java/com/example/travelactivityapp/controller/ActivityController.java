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
    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.saveActivity(activity));
    }

    // Get all activities
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }



    // Get activity by ID - keep or adjust to activity name?
//    @GetMapping("/{id}")
//    public ResponseEntity<Activity> getActivityById(@Valid @PathVariable Long id) {
//        Optional<Activity> activity = activityService.getActivityById(id);
//        if (activity.isPresent()) {
//            return ResponseEntity.ok(activity.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // Update/Save Activity


    // Update Activity - keep this?
    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@Valid @PathVariable Long id, @RequestBody Activity activityDetails) {
        return ResponseEntity.ok(activityService.updateActivity(id, activityDetails));
    }

    // Delete Activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@Valid @PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
