package com.example.travelactivityapp.service;

// This service layer class is responsible for handling the business logic for the Activity entity, including CRUD

import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j // Lombok annotation to generate a logger
@Service // This class is a service
@Transactional // This class is transactional
public class ActivityService {

    @Autowired 
    IActivityRepository activityRepository; // Injected the activity repository

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity); // Save the activity to the database
    }

    // Get all activities
    public List<Activity> getAllActivities() { 
        return activityRepository.findAll(); 
    }

    // Get activity by ID 
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // Update Activity 
    public Activity updateActivity(Long id, Activity activityDetails) {
        // Find the activity by id
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));
        // Set the activity details
        activity.setActivityName(activityDetails.getActivityName());
        activity.setDescription(activityDetails.getDescription());
        activity.setLocation(activityDetails.getLocation());
        activity.setStartTime(activityDetails.getStartTime());
        activity.setEndTime(activityDetails.getEndTime());

        return activityRepository.save(activity); // save the updated activity
    }

    // Delete Activity
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}
