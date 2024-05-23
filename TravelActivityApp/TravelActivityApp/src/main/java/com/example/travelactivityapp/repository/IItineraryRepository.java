package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUserId(Integer userId);
}
