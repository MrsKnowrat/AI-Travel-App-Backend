package com.example.travelactivityapp.controller;

/* This class manages profile management (CRUD), data validation, and integration
* with other services. */

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.service.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Enables logging within the class
@RestController // Marks class as controller; methods return domain object
@Validated // Ensures beans are validated before processing
@CrossOrigin(origins = "*") // Allows c/o requests from all domains
@RequestMapping("/profiles") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class ProfileController {

    @Autowired // Handles business logic for user profile-related ops
    ProfileService profileService;

    // Get User Profile by ID - returns RE containing list of profile objects
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Profile>> getProfileByUserId(@Valid @PathVariable Long id) { // Returns response entity with list of profiles
        List<Profile> profile = profileService.getProfileByUserId(id); // Gets profile by user id
        if (profile.isEmpty()) { // If profile is empty
            throw new RuntimeException("No profiles found for the user with ID: " + id); // Throws exception if no profiles found
        }
        return ResponseEntity.ok(profile); // Returns response entity with profile in body
    }

    // Update Profile - returns RE containing profile object
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@Valid @PathVariable Long id, @RequestBody Profile profileDetails) { // Returns response entity with updated profile
        Profile updatedProfile = profileService.updateProfile(id, profileDetails); // Updates profile
        return ResponseEntity.ok(updatedProfile); // Returns response entity with updated profile
    }

    // Delete Profile & User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@Valid @PathVariable Long id) { // Returns response entity with no content
        profileService.deleteProfile(id); // Deletes profile
        return ResponseEntity.noContent().build(); // Returns response entity with no content
    }

}
