package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.repository.IProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private IProfileRepository profileRepository;

    public Profile updateProfile(Integer id, Profile profile) {
        Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        existingProfile.setBio(profile.getBio());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        existingProfile.setPreferences(profile.getPreferences());
        existingProfile.setAddress(profile.getAddress());
        return profileRepository.save(existingProfile);
    }

}
