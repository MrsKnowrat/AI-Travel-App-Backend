package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Profile>> getProfileByUserId(@PathVariable Integer userId) {
        List<Profile> profile = profileService.getProfileByUserId(userId);
        if (profile.isEmpty()) {
            throw new RuntimeException("No profiles found for the user with ID: " + userId);
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Integer id, @RequestBody Profile profileDetails) {
        Profile updatedProfile = profileService.updateProfile(id, profileDetails);
        return ResponseEntity.ok(updatedProfile);
    }

    // Delete Profile & User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Integer id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
