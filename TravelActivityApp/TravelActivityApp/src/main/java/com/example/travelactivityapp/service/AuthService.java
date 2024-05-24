package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IProfileRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class AuthService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IProfileRepository profileRepository;

    public User signup(User user) {
        // Create and save the profile
        Profile profile = new Profile();
        profile.setBio("Default bio"); // Set default values for the profile
        profile.setProfilePicture("default.png");
        profile.setPreferences("default preferences");
        Profile savedProfile = profileRepository.save(profile);

        // Link the profile to the user and save the user
        user.setProfile(savedProfile);
        return userRepository.save(user);
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
