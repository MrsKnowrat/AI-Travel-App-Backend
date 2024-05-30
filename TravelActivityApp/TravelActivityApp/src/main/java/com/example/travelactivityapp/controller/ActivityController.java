package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/activities")
@Validated
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    // Create Activity
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.saveActivity(activity));
    }

    // Get all activities
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }



    // Get activity by ID - keep or adjust to activity name?
//    @GetMapping("/{id}")
//    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
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
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activityDetails) {
        return ResponseEntity.ok(activityService.updateActivity(id, activityDetails));
    }

    // Delete Activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
