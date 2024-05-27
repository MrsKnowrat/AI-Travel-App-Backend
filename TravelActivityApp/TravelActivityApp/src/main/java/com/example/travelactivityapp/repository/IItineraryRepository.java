package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItineraryRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findItinerariesByUserId(Long id);

    List<Itinerary> findAllByUser_Id(Long userId);

}
