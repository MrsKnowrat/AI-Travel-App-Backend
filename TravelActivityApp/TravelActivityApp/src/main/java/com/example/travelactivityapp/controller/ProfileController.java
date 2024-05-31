package com.example.travelactivityapp.controller;

/* This class handles  */

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
    private ProfileService profileService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Profile>> getProfileByUserId(@Valid @PathVariable Long id) {
        List<Profile> profile = profileService.getProfileByUserId(id);
        if (profile.isEmpty()) {
            throw new RuntimeException("No profiles found for the user with ID: " + id);
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@Valid @PathVariable Long id, @RequestBody Profile profileDetails) {
        Profile updatedProfile = profileService.updateProfile(id, profileDetails);
        return ResponseEntity.ok(updatedProfile);
    }

    // Delete Profile & User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@Valid @PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
