package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.repository.IItineraryRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ItineraryService {
    @Autowired
    private IItineraryRepository itineraryRepository;

    @Autowired
    private IUserRepository userRepository;

    public Itinerary saveItinerary(Itinerary itinerary, Integer userId) {
        itinerary.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> getUserItinerariesByUserId(Integer userId) {
        List<Itinerary> itineraries = itineraryRepository.findItinerariesByUserUserId(userId);
        if (itineraries.isEmpty()) {
            throw new RuntimeException("No itineraries found for the user with ID: " + userId);
        }
        return itineraries;
    }

}
