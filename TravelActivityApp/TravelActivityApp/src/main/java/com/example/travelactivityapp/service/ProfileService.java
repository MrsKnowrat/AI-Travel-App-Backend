package com.example.travelactivityapp.service;

// This service layer class is responsible for handling the business logic for the Profile entity, including CRUD

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.repository.IProfileRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j // Lombok annotation to generate a logger
@Service // This class is a service
@Transactional // This class is transactional
public class ProfileService {

    @Autowired
    IProfileRepository profileRepository; // This is the repository layer class for the Profile entity

    @Autowired
    IUserRepository userRepository; // This is the repository layer class for the User entity

    // Get Profile by User ID
    public List<Profile> getProfileByUserId(Long id) {
        List<Profile> profiles = profileRepository.getProfileByUserId(id); // Get the profile by user ID
        if (profiles.isEmpty()) { // If the profiles list is empty
            throw new RuntimeException("No profiles found for the user with ID: " + id); // Throw a runtime exception
        }
        return profiles; // Return the profiles
    }   

    // Update Profile
    public Profile updateProfile(Long id, Profile profile) {
        Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found")); // Find the profile by ID
        existingProfile.setBio(profile.getBio()); // Set the new bio
        existingProfile.setProfilePicture(profile.getProfilePicture()); // Set the new profile picture
        existingProfile.setPreferences(profile.getPreferences()); // Set the new preferences
        return profileRepository.save(existingProfile); // Save the existing profile
    }

    // Delete Profile & User
    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found")); // Find the profile by ID
        if (profile.getUser() != null) { // If the profile has a user
            userRepository.delete(profile.getUser()); // Delete the user
        }
        profileRepository.delete(profile); // Delete the profile
    }
}
