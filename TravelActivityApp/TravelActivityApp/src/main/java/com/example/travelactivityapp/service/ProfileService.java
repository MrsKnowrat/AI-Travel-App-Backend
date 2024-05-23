package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.repository.IProfileRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;

    @Autowired
    private IUserRepository userRepository;

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> getProfileByUserId(Integer userId) {
        List<Profile> profiles = profileRepository.getProfileByUserUserId(userId);
        if (profiles.isEmpty()) {
            throw new RuntimeException("No profiles found for the user with ID: " + userId);
        }
        return profiles;
    }

    public Profile updateProfile(Integer id, Profile profile) {
        Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        existingProfile.setBio(profile.getBio());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        existingProfile.setPreferences(profile.getPreferences());
        existingProfile.setAddress(profile.getAddress());
        return profileRepository.save(existingProfile);
    }

    // Delete Profile & User
    public void deleteProfile(Integer id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        if (profile.getUser() != null) {
            userRepository.delete(profile.getUser());
        }
        profileRepository.delete(profile);
    }
}
