package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.repository.IProfileRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class ProfileService {

    @Autowired
    IProfileRepository profileRepository;

    @Autowired
    IUserRepository userRepository;

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> getProfileByUserId(Long id) {
        List<Profile> profiles = profileRepository.getProfileByUserUserId(id);
        if (profiles.isEmpty()) {
            throw new RuntimeException("No profiles found for the user with ID: " + id);
        }
        return profiles;
    }

    public Profile updateProfile(Long id, Profile profile) {
        Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        existingProfile.setBio(profile.getBio());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        existingProfile.setPreferences(profile.getPreferences());
        return profileRepository.save(existingProfile);
    }

    // Delete Profile & User
    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        if (profile.getUser() != null) {
            userRepository.delete(profile.getUser());
        }
        profileRepository.delete(profile);
    }
}
