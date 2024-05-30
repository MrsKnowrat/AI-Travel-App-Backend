package com.example.travelactivityapp.service;

import com.example.travelactivityapp.dto.ItineraryDTO;
import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IItineraryRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import com.example.travelactivityapp.util.ModelMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Slf4j
@Service
@Transactional
public class ItineraryService {

    @Autowired
    IItineraryRepository itineraryRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ModelMapperUtil modelMapperUtil;

    private String apiKey;

    // initialize apiKey, a service-level property
    public ItineraryService() {
        this.apiKey = loadApiKey();
    }

    // Loads API Key allowing for access to Open AI-sourced itineraries
    private String loadApiKey() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Unable to find config.properties");
            }
            prop.load(input);
            return prop.getProperty("openai.api.key");
        } catch (IOException ex) {
            throw new IllegalStateException("Error reading config.properties", ex);
        }
    }

     public String callOpenAiApi(String modelName, String userPrompt, String apiKey) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = String.format(
                "{\"model\": \"%s\", \"messages\": [{\"role\": \"system\", \"content\": \"%s\"}, {\"role\": \"user\", \"content\": \"%s\"}], \"response_format\": {\"type\": \"json_object\"}}",
                modelName,
                "you are a helpful travel planner, planning activities for a wide variety of users matching their wants and needs. you are also going to tag the activities with things like family friendly or kid friendly or pets allowed or short lines or whatever tags make sense. please respond with a json object in the format of {activities: [{activity: activity name, tags: [tag1, tag2, tag3]}, {activity: activity name, tags: [tag1, tag2, tag3]}]}",
                "here are my trip preferences: " +userPrompt.replace("\"", "\\\"")); // Escape double quotes in userPrompt
    
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public Itinerary createItineraryForUser(ItineraryDTO itineraryDTO) {
        User user = userRepository.findUserByUsername(itineraryDTO.getUsername()).orElseThrow(
                () -> new RuntimeException("User with email " + itineraryDTO.getUsername() + " not found"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule

        try {
            // Convert ItineraryDTO to JSON string
            String itineraryJson = objectMapper.writeValueAsString(itineraryDTO);

            String response = callOpenAiApi("gpt-4o", itineraryJson,
                    apiKey);

            System.out.println(response);
            // TODO 1
            // Process the response to extract activities and tags
            // save the activities to the db
            // save the tags to the db

        } catch (Exception e) {
            e.printStackTrace();
        }

        Itinerary newItinerary = modelMapperUtil.map(itineraryDTO, Itinerary.class);
        newItinerary.setUser(user);

        // TODO 2
        // might need to update the itinerary with the activities and tags
        // end goal is that the response here that gets sent to the front end includes
        // the itinerary with activities and tags
        return itineraryRepository.save(newItinerary);

        // TODO 3
        // on the front end javascript codebase, when the home page loads itineraries,
        // show activites and tags
    }

    // Get Itinerary By Username - might delete this
    public List<Itinerary> getAllItinerariesByUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
        return itineraryRepository.findAllByUser_id(user.getId());
    }

    // keep this?
    // public List<Itinerary> getUserItinerariesByUserId(Long id) {
    // List<Itinerary> itineraries =
    // itineraryRepository.findItinerariesByUserId(id);
    // if (itineraries.isEmpty()) {
    // throw new RuntimeException("No itineraries found for the user with ID: " +
    // id);
    // }
    // return itineraries;
    // }

    // Update/Save Itinerary
    public Itinerary saveItinerary(Itinerary itinerary, Long id) {
        itinerary.setUser(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
        return itineraryRepository.save(itinerary);
    }

    // Delete Itinerary

}
