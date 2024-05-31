package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItineraryRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findItinerariesByUserId(Long id);

    List<Itinerary> findAllByUser_id(Long userId);

    @Query("SELECT i FROM Itinerary i WHERE i.user.username = :username")
    List<Itinerary> findItinerariesByUsername(@Param("username") String username);

    // Custom JPQL query to fetch itineraries with activities for a specific user
    @Query("SELECT DISTINCT i FROM Itinerary i LEFT JOIN FETCH i.activities WHERE i.user.username = :username")
    List<Itinerary> findAllItinerariesWithActivitiesByUsername(@Param("username") String username);
}