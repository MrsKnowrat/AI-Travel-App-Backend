package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ActivityService {

    @Autowired
    IActivityRepository activityRepository;

    /* CRUD
    C- Included here
    R- Included here
    U- Included here
    D- Included here
    */

    // Create Activity


    // Get all activities
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Get activity by ID - keep or adjust to activity name?
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // Update/Save Activity
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // Update Activity - keep this?
    public Activity updateActivity(Long id, Activity activityDetails) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));

        activity.setActivityName(activityDetails.getActivityName());
        activity.setDescription(activityDetails.getDescription());
        activity.setLocation(activityDetails.getLocation());
        activity.setStartTime(activityDetails.getStartTime());
        activity.setEndTime(activityDetails.getEndTime());

        return activityRepository.save(activity);
    }

    // Delete Activity
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}
