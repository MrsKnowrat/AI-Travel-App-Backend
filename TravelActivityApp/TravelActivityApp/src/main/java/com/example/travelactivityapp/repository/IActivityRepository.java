package com.example.travelactivityapp.repository;

// This is the repository interface for the Activity model

import com.example.travelactivityapp.model.Activity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // This is a Spring annotation that marks the interface as a repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {
    // @Query("SELECT a FROM Activity a LEFT JOIN FETCH a.tags WHERE a.itinerary.id = :itineraryId") - without this comment, the app won't run
    // List<Activity> findActivitiesWithTagsByItineraryId(@Param("itineraryId") Long itineraryId); - without this comment, the app won't run
}
