package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository; // dependency injection.

    @Autowired
    private ProfileService profileService;

    // Create New User & Profile
    public User signup(User user) {
        // Create and save the profile
        Profile profile = new Profile();
        profile.setBio("Default bio"); // Set default values for the profile
        profile.setProfilePicture("default.png");
        profile.setPreferences("default preferences");
        Profile savedProfile = profileService.createProfile(profile);

        // Link the profile to the user and save the user
        user.setProfile(savedProfile);
        return userRepository.save(user);
    }
    // Read/Get User by Username
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    // Update user
    public User updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setDateOfBirth(userDetails.getDateOfBirth());

        return userRepository.save(user);
    }
    // No need to include User delete method, as it will be deleted of the User deletes their profile
}
