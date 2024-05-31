package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {
    // @Query("SELECT a FROM Activity a LEFT JOIN FETCH a.tags WHERE a.itinerary.id = :itineraryId")
    // List<Activity> findActivitiesWithTagsByItineraryId(@Param("itineraryId") Long itineraryId);
}
