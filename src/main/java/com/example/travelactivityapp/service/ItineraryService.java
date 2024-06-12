package com.example.travelactivityapp.service;

// This service layer class is responsible for handling the business logic for the Itinerary entity, including CRUD

import com.example.travelactivityapp.dto.ActivityDTO;
import com.example.travelactivityapp.dto.ItineraryDTO;
import com.example.travelactivityapp.dto.TagDTO;
import com.example.travelactivityapp.model.Activity;
import com.example.travelactivityapp.model.Itinerary;
import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IActivityRepository;
import com.example.travelactivityapp.repository.IItineraryRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import com.example.travelactivityapp.repository.ITagRepository;
import com.example.travelactivityapp.util.ModelMapperUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Slf4j // Lombok annotation to generate a logger
@Service // This class is a service
@Transactional // This class is transactional
public class ItineraryService {

    @Autowired
    IItineraryRepository itineraryRepository; 

    @Autowired
    IActivityRepository activityRepository;

    @Autowired
    ITagRepository tagRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ModelMapperUtil modelMapperUtil; // This is the utility class for mapping between entities and DTOs

    private String apiKey; // This is the API key for the Open AI API
   
    // Get Itineraries by Username
    public List<ItineraryDTO> getItinerariesByUsername(String username) {
        log.info("Fetching itineraries for username: {}", username); // Log the username
        List<Itinerary> itineraries = itineraryRepository.findItinerariesByUsername(username); // Find the itineraries by username
        log.info("Number of itineraries fetched: {}", itineraries.size()); // Log the number of itineraries fetched
    
        return itineraries.stream() // Stream the itineraries
                .map(this::convertToItineraryDTO) // Map each itinerary to a DTO
                .peek(dto -> log.info("Converted ItineraryDTO: {}", dto)) // Log each DTO
                .collect(Collectors.toList()); // Collect the DTOs into a list
    }
    
    private ItineraryDTO convertToItineraryDTO(Itinerary itinerary) { // This method converts an Itinerary entity to a DTO
        log.info("Converting Itinerary ID: {} to DTO", itinerary.getId()); // Log the ID of the itinerary being converted
    
        List<ActivityDTO> activityDTOs = itinerary.getActivities().stream() // Stream the activities of the itinerary
                                                   .map(this::convertToActivityDTO) // Map each activity to a DTO
                                                   .collect(Collectors.toList()); // Collect the DTOs into a list
    
        ItineraryDTO dto = ItineraryDTO.builder() // Build the DTO
                .itineraryName(itinerary.getItineraryName()) // Set the itinerary name
                .description(itinerary.getDescription()) // Set the description
                .startDate(itinerary.getStartDate()) // Set the start date
                .endDate(itinerary.getEndDate()) // Set the end date
                .username(itinerary.getUser().getUsername()) // Set the username
                .activities(activityDTOs) // Set activities with the converted list
                .build(); // Build the DTO
    
        log.info("Completed conversion for Itinerary ID: {}", itinerary.getId()); // Log the completion of the conversion
        return dto; // Return the DTO
    }
    
    // This method converts an Activity entity to a DTO
    private ActivityDTO convertToActivityDTO(Activity activity) { 
        log.info("Converting Activity ID: {} to DTO", activity.getId()); // Log the ID of the activity being converted
        List<TagDTO> tagDTOs = activity.getTags().stream() // Stream the tags of the activity
                .map(tag -> TagDTO.builder() // Build the DTO
                        .tagName(tag.getTagName()) // Set the tag name
                        .build()) // Build the DTO
                .collect(Collectors.toList()); // Collect the DTOs into a list
    
        ActivityDTO dto = ActivityDTO.builder() // Build the DTO
                .activityName(activity.getActivityName()) // Set the activity name
                .tags(tagDTOs) // Set the tags
                .build(); // Build the DTO
        log.info("Completed conversion for Activity ID: {}", activity.getId()); // Log the completion of the conversion
        return dto; // Return the DTO
    }
    
    // This method converts a Tag entity to a DTO
    private TagDTO convertToTagDTO(Tag tag) { 
        log.info("Converting Tag ID: {} to DTO", tag.getId()); // Log the ID of the tag being converted
        TagDTO dto = TagDTO.builder() // Build the DTO
                .tagName(tag.getTagName()) // Set the tag name
                .build(); // Build the DTO
        log.info("Completed conversion for Tag ID: {}", tag.getId()); // Log the completion of the conversion
        return dto; // Return the DTO
    }

    // initialize apiKey, a service-level property
    public ItineraryService() { // This constructor is used to initialize the API key
        this.apiKey = loadApiKey(); // Load the API key
    }

    // Loads API Key allowing for access to Open AI-sourced itineraries
    private String loadApiKey() { // This method loads the API key from the config.properties file
        Properties prop = new Properties(); // Create a Properties object
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) { // Try to load the config.properties file
            if (input == null) { // If the input is null
                throw new IllegalStateException("Unable to find config.properties");
            }
            prop.load(input);
            return prop.getProperty("openai.api.key");
        } catch (IOException ex) {
            throw new IllegalStateException("Error reading config.properties", ex);
        }
    }

    // This method calls the Open AI API to generate a list of activities and tags based on the user's trip preferences
    public String callOpenAiApi(String modelName, String userPrompt, String apiKey) throws Exception { 
        HttpClient client = HttpClient.newHttpClient(); // Create a new HttpClient
        String requestBody = String.format( // Create the request body
                // Set the request body
                "{\"model\": \"%s\", \"messages\": [{\"role\": \"system\", \"content\": \"%s\"}, {\"role\": \"user\", \"content\": \"%s\"}], \"response_format\": {\"type\": \"json_object\"}}", 
                modelName, // Set the model name
                // Set the system prompt
                "you are a helpful travel planner, planning activities for a wide variety of users matching their wants and needs. you are also going to tag the activities with things like family friendly or kid friendly or pets allowed or short lines or whatever tags make sense. please respond with a json object in the format of {activities: [{activityName: activity name, tags: [tag1, tag2, tag3]}, {activityName: activity name, tags: [tag1, tag2, tag3]}]}", 
                "here are my trip preferences: " + userPrompt.replace("\"", "\\\"")); // Escape double quotes in userPrompt
                                                                                      
        HttpRequest request = HttpRequest.newBuilder() // Create a new HttpRequest
                .uri(URI.create("https://api.openai.com/v1/chat/completions")) // Set the URI
                .header("Content-Type", "application/json") // Set the content type
                .header("Authorization", "Bearer " + apiKey) // Set the authorization header
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // Set the body
                .build(); // Build the request

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Send the request and get the response
        return response.body(); // Return the response body
    }

    // This method processes the response from the Open AI API
    public void processApiResponse(String itineraryJson, String apiKey) { 
        try {
            String response = callOpenAiApi("gpt-4o", itineraryJson, apiKey); // Call the Open AI API
            System.out.println("API Response: " + response); // Debug: Print the full API response

            ObjectMapper objectMapper = new ObjectMapper(); // Create a new ObjectMapper
 
            // Parse the initial JSON response
            JsonNode rootNode = objectMapper.readTree(response); // Parse the response into a JsonNode
            String activitiesJson = rootNode.path("choices").get(0).path("message").path("content").asText(); // Extract the activities JSON from the response
            System.out.println("Extracted Activities JSON: " + activitiesJson); // Debug: Print the extracted activities
                                                                                // JSON

            // Parse the activities JSON content
            JsonNode activitiesNode = objectMapper.readTree(activitiesJson); // Parse the activities JSON into a JsonNode
            List<ActivityDTO> activityDTOs = objectMapper.convertValue(activitiesNode.path("activities"), // Convert the activities JSON to a List of ActivityDTOs
                    new TypeReference<List<ActivityDTO>>() {
                    });
            System.out.println("Parsed Activities DTOs: " + activityDTOs); // Debug: Print the parsed ActivityDTOs

            for (ActivityDTO activityDTO : activityDTOs) { // Process each ActivityDTO
                Activity activity = new Activity(); // Create a new Activity
                activity.setActivityName(activityDTO.getActivityName()); // Set the activity name
                System.out.println("Processing Activity: " + activityDTO.getActivityName()); // Debug: Print each activity name being processed
                                                                                           
                Set<Tag> tags = new HashSet<>(); // Create a new HashSet for the tags
                for (TagDTO tagDTO : activityDTO.getTags()) { // Process each TagDTO
                    Tag tag = new Tag(); // Create a new Tag
                    tag.setTagName(tagDTO.getTagName()); // Set the tag name
                    // Save each tag before adding to activity
                    tag = tagRepository.save(tag); // Assuming you have a tagRepository
                    tags.add(tag); // Add the tag to the tags set
                    System.out.println("Adding Tag: " + tagDTO.getTagName()); // Debug: Print each tag being added
                }
                activity.setTags(tags); // Set the tags for the activity
                activityRepository.save(activity); // Save the activity
                System.out.println("Activity Saved: " + activity.getActivityName()); // Debug: Confirm each activity saved
                                                  
            }
        } catch (Exception e) { // Catch any exceptions
            System.out.println("Error processing API response: " + e.getMessage()); // Debug: Print error message
            e.printStackTrace(); // Print the stack trace of the exception
        }
    }

    public Itinerary createItineraryForUser(ItineraryDTO itineraryDTO) { // This method creates an itinerary for a user
        User user = userRepository.findUserByUsername(itineraryDTO.getUsername()).orElseThrow( // Find the user by username
                () -> new RuntimeException("User with email " + itineraryDTO.getUsername() + " not found")); // If the user is not found, throw a runtime exception

        ObjectMapper objectMapper = new ObjectMapper(); // Create a new ObjectMapper
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule

        try {
            // Convert ItineraryDTO to JSON string
            String itineraryJson = objectMapper.writeValueAsString(itineraryDTO); // Convert the ItineraryDTO to a JSON string

            processApiResponse(itineraryJson, apiKey); // Process the API response

            Itinerary newItinerary = modelMapperUtil.map(itineraryDTO, Itinerary.class); // Map the ItineraryDTO to a new Itinerary
            newItinerary.setUser(user); // Set the user for the new itinerary

            // Add activities to the itinerary
            List<Activity> activities = activityRepository.findAll(); // This is just an example, I might want to select specific activities
            newItinerary.setActivities(new HashSet<>(activities)); // Set the activities for the new itinerary

            return itineraryRepository.save(newItinerary); // Save the new itinerary
        } catch (Exception e) { // Catch any exceptions
            log.error("Exception occurred: ", e); // Log the error
            throw new RuntimeException("Error processing request", e); // Throw a runtime exception
        }
    }

    // Update Itinerary
    public Itinerary updateItinerary(Itinerary itinerary, Long id) throws Exception { 
        try {
            Itinerary existingItinerary = itineraryRepository.findById(id) // Find the existing itinerary by ID
                    .orElseThrow(() -> new RuntimeException("Itinerary not found")); // If the itinerary is not found, throw a runtime exception
            // Update itinerary details here
            existingItinerary.setItineraryName(itinerary.getItineraryName()); // Set the itinerary name
            existingItinerary.setDescription(itinerary.getDescription()); // Set the description
            existingItinerary.setStartDate(itinerary.getStartDate()); // Set the start date
            existingItinerary.setEndDate(itinerary.getEndDate()); // Set the start date
            existingItinerary.setActivities(itinerary.getActivities()); // Assuming activities are already updated
            existingItinerary.setTags(itinerary.getTags()); // Assuming tags are already updated

            return itineraryRepository.save(existingItinerary); // Save the existing itinerary
        } catch (Exception e) { // Catch any exceptions
            log.error("Error updating itinerary: " + e.getMessage()); // Log the error
            throw new Exception("Failed to update itinerary", e); // Throw a runtime exception
        }
    }

    // TODO 3
    // on the front end javascript codebase, when the home page loads itineraries,
    // show activities and tags

    // public List<ItineraryDTO> getAllItinerariesByUser(String username) {
    //     try {
    //         log.info("Fetching user by username: {}", username);
    //         User user = userRepository.findUserByUsername(username)
    //                 .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
    //         log.info("User found with ID: {}", user.getId());
            
    //         log.info("Fetching itineraries for user ID: {}", user.getId());
    //         List<Itinerary> itineraries = itineraryRepository.findAllByUser_id(user.getId());
    //         log.info("Fetched {} itineraries for user ID: {}", itineraries.size(), user.getId());
            
    //         log.info("Starting conversion of itineraries to DTOs");
    //         List<ItineraryDTO> dtos = itineraries.stream()
    //                 .map(this::convertToItineraryDTO)
    //                 .peek(dto -> log.info("Converted ItineraryDTO: {}", dto))
    //                 .collect(Collectors.toList());
    //         log.info("Completed conversion of itineraries to DTOs");
            
    //         return dtos;
    //     } catch (Exception e) {
    //         log.error("Error fetching itineraries for user: {}", username, e);
    //         throw new RuntimeException("Failed to fetch itineraries", e);
    //     }
    // }

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

    // Delete Itinerary

}
