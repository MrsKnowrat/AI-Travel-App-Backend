package com.example.travelactivityapp.service;

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

@Slf4j
@Service
@Transactional
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
    ModelMapperUtil modelMapperUtil;

    private String apiKey;

    // public List<ActivityDTO> getActivitiesWithTagsByItineraryId(Long itineraryId) {
    //     List<Activity> activities = activityRepository.findActivitiesWithTagsByItineraryId(itineraryId);
    //     return activities.stream()
    //                      .map(this::convertToActivityDTO)
    //                      .collect(Collectors.toList());
    // }

    public List<ItineraryDTO> getItinerariesByUsername(String username) {
        log.info("Fetching itineraries for username: {}", username);
        List<Itinerary> itineraries = itineraryRepository.findItinerariesByUsername(username);
        log.info("Number of itineraries fetched: {}", itineraries.size());
    
        return itineraries.stream()
                .map(this::convertToItineraryDTO)
                .peek(dto -> log.info("Converted ItineraryDTO: {}", dto))
                .collect(Collectors.toList());
    }
    
    private ItineraryDTO convertToItineraryDTO(Itinerary itinerary) {
        log.info("Converting Itinerary ID: {} to DTO", itinerary.getId());
    
        List<ActivityDTO> activityDTOs = itinerary.getActivities().stream()
                                                   .map(this::convertToActivityDTO)
                                                   .collect(Collectors.toList());
    
        ItineraryDTO dto = ItineraryDTO.builder()
                .itineraryName(itinerary.getItineraryName())
                .description(itinerary.getDescription())
                .startDate(itinerary.getStartDate())
                .endDate(itinerary.getEndDate())
                .username(itinerary.getUser().getUsername())
                .activities(activityDTOs)  // Set activities with the converted list
                .build();
    
        log.info("Completed conversion for Itinerary ID: {}", itinerary.getId());
        return dto;
    }
    
    private ActivityDTO convertToActivityDTO(Activity activity) {
        log.info("Converting Activity ID: {} to DTO", activity.getId());
        List<TagDTO> tagDTOs = activity.getTags().stream()
                .map(tag -> TagDTO.builder()
                        .tagName(tag.getTagName())
                        .build())
                .collect(Collectors.toList());
    
        ActivityDTO dto = ActivityDTO.builder()
                .activityName(activity.getActivityName())
                .tags(tagDTOs)
                .build();
        log.info("Completed conversion for Activity ID: {}", activity.getId());
        return dto;
    }
    
    private TagDTO convertToTagDTO(Tag tag) {
        log.info("Converting Tag ID: {} to DTO", tag.getId());
        TagDTO dto = TagDTO.builder()
                .tagName(tag.getTagName())
                .build();
        log.info("Completed conversion for Tag ID: {}", tag.getId());
        return dto;
    }

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
                "you are a helpful travel planner, planning activities for a wide variety of users matching their wants and needs. you are also going to tag the activities with things like family friendly or kid friendly or pets allowed or short lines or whatever tags make sense. please respond with a json object in the format of {activities: [{activityName: activity name, tags: [tag1, tag2, tag3]}, {activityName: activity name, tags: [tag1, tag2, tag3]}]}",
                "here are my trip preferences: " + userPrompt.replace("\"", "\\\"")); // Escape double quotes in
                                                                                      // userPrompt

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // TODO 1
    // Process the response to extract activities and tags
    // save the activities to the db
    // save the tags to the db
    // handles the response from the open ai api call and saves the activities to
    // the db
    public void processApiResponse(String itineraryJson, String apiKey) {
        try {
            String response = callOpenAiApi("gpt-4o", itineraryJson, apiKey);
            System.out.println("API Response: " + response); // Debug: Print the full API response

            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the initial JSON response
            JsonNode rootNode = objectMapper.readTree(response);
            String activitiesJson = rootNode.path("choices").get(0).path("message").path("content").asText();
            System.out.println("Extracted Activities JSON: " + activitiesJson); // Debug: Print the extracted activities
                                                                                // JSON

            // Parse the activities JSON content
            JsonNode activitiesNode = objectMapper.readTree(activitiesJson);
            List<ActivityDTO> activityDTOs = objectMapper.convertValue(activitiesNode.path("activities"),
                    new TypeReference<List<ActivityDTO>>() {
                    });
            System.out.println("Parsed Activities DTOs: " + activityDTOs); // Debug: Print the parsed ActivityDTOs

            for (ActivityDTO activityDTO : activityDTOs) {
                Activity activity = new Activity();
                activity.setActivityName(activityDTO.getActivityName());
                System.out.println("Processing Activity: " + activityDTO.getActivityName()); // Debug: Print each
                                                                                             // activity name being
                                                                                             // processed

                Set<Tag> tags = new HashSet<>();
                for (TagDTO tagDTO : activityDTO.getTags()) {
                    Tag tag = new Tag();
                    tag.setTagName(tagDTO.getTagName());
                    // Save each tag before adding to activity
                    tag = tagRepository.save(tag); // Assuming you have a tagRepository
                    tags.add(tag);
                    System.out.println("Adding Tag: " + tagDTO.getTagName()); // Debug: Print each tag being added
                }
                activity.setTags(tags);
                activityRepository.save(activity);
                System.out.println("Activity Saved: " + activity.getActivityName()); // Debug: Confirm each activity
                                                                                     // saved
            }
        } catch (Exception e) {
            System.out.println("Error processing API response: " + e.getMessage()); // Debug: Print error message
            e.printStackTrace();
        }
    }

    public Itinerary createItineraryForUser(ItineraryDTO itineraryDTO) {
        User user = userRepository.findUserByUsername(itineraryDTO.getUsername()).orElseThrow(
                () -> new RuntimeException("User with email " + itineraryDTO.getUsername() + " not found"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule

        try {
            // Convert ItineraryDTO to JSON string
            String itineraryJson = objectMapper.writeValueAsString(itineraryDTO);

            processApiResponse(itineraryJson, apiKey);

            Itinerary newItinerary = modelMapperUtil.map(itineraryDTO, Itinerary.class);
            newItinerary.setUser(user);

            // Add activities to the itinerary
            List<Activity> activities = activityRepository.findAll(); // This is just an example, you might want to
                                                                      // select specific activities
            newItinerary.setActivities(new HashSet<>(activities));

            return itineraryRepository.save(newItinerary);
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
            throw new RuntimeException("Error processing request", e);
        }
    }

    // TODO 2
    // might need to update the itinerary with the activities and tags
    // end goal is that the response here that gets sent to the front end includes
    // the itinerary with activities and tags
    // Update/Save Itinerary
    public Itinerary updateItinerary(Itinerary itinerary, Long id) throws Exception {
        try {
            Itinerary existingItinerary = itineraryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Itinerary not found"));
            // Update itinerary details here
            existingItinerary.setItineraryName(itinerary.getItineraryName());
            existingItinerary.setDescription(itinerary.getDescription());
            existingItinerary.setStartDate(itinerary.getStartDate());
            existingItinerary.setEndDate(itinerary.getEndDate());
            existingItinerary.setActivities(itinerary.getActivities()); // Assuming activities are already updated
            existingItinerary.setTags(itinerary.getTags()); // Assuming tags are already updated

            return itineraryRepository.save(existingItinerary);
        } catch (Exception e) {
            log.error("Error updating itinerary: " + e.getMessage());
            throw new Exception("Failed to update itinerary", e);
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
