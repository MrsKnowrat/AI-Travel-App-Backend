package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActivityService {

    @Autowired
    private IActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Integer id) {
        return activityRepository.findById(id);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Integer id) {
        activityRepository.deleteById(id);
    }

    public Activity updateActivity(Integer id, Activity activityDetails) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));

        activity.setActivityName(activityDetails.getActivityName());
        activity.setDescription(activityDetails.getDescription());
        activity.setLocation(activityDetails.getLocation());
        activity.setStartTime(activityDetails.getStartTime());
        activity.setEndTime(activityDetails.getEndTime());

        return activityRepository.save(activity);
    }
}
