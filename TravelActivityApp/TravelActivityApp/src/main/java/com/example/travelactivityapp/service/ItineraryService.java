package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.repository.IItineraryRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ItineraryService {

    @Autowired
    IItineraryRepository itineraryRepository;

    @Autowired
    IUserRepository userRepository;

    public Itinerary saveItinerary(Itinerary itinerary, Long id) {
        itinerary.setUser(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> getUserItinerariesByUserId(Long id) {
        List<Itinerary> itineraries = itineraryRepository.findItinerariesByUserUserId(id);
        if (itineraries.isEmpty()) {
            throw new RuntimeException("No itineraries found for the user with ID: " + id);
        }
        return itineraries;
    }

}
