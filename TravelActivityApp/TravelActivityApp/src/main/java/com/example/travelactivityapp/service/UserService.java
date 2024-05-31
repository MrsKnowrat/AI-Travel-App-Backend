package com.example.travelactivityapp.service;

// This service layer class is responsible for handling the business logic for the User entity, including CRUD

import ch.qos.logback.core.model.Model;
import com.example.travelactivityapp.dto.UserLoginDTO;
import com.example.travelactivityapp.dto.UserRegistrationDTO;
import com.example.travelactivityapp.dto.UserUpdateDTO;
import com.example.travelactivityapp.model.Profile;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IProfileRepository;
import com.example.travelactivityapp.repository.IUserRepository;
import com.example.travelactivityapp.util.ModelMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j // Lombok annotation to generate a logger
@Service // This class is a service
@Transactional // This class is transactional
public class UserService {

    @Autowired
    IUserRepository userRepository; // dependency injection.

    @Autowired
    ModelMapperUtil modelMapperUtil; 

    @Autowired
    IProfileRepository profileRepository;// This service layer class is responsible for handling the business logic for the Activity entity, including CRUD

    @Transactional
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        try {
            User newUser = modelMapperUtil.map(userRegistrationDTO, User.class);
            newUser.setPassword(userRegistrationDTO.getPassword()); // Set the password directly from DTO

            // Create and set the profile
            Profile newProfile = new Profile();
            // Set default values or use DTO to set profile details
            newProfile.setFirstName(userRegistrationDTO.getFirstName());
            newProfile.setLastName(userRegistrationDTO.getLastName());
            newProfile.setDateOfBirth(userRegistrationDTO.getDateOfBirth());
            newProfile.setUser(newUser);
            Profile savedProfile = profileRepository.save(newProfile); // Save the profile first
            newUser.setProfile(savedProfile); // Set the saved profile to the user
            return userRepository.save(newUser); // Saves both User and Profile due to CascadeType.ALL
        } catch (Exception e) {
            if (e.getMessage().contains("Violates unique constraint")) { // If the exception message contains a unique constraint violation
                throw new RuntimeException("Username or email you provided already exists"); // Throw a runtime exception
            }
            throw new RuntimeException(e.getLocalizedMessage(), e); // Throw a runtime exception with the localized message and the original exception
        }
    }

    public User loginUser(UserLoginDTO userLoginDTO) {
        User existingUser = userRepository.findUserByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new RuntimeException("User with email " + userLoginDTO.getEmail() + " not found")); // Find the user by email
        if (!userLoginDTO.getPassword().equals(existingUser.getPassword())) { // If the provided password does not match the existing user's password
            throw new RuntimeException("The provided password is incorrect"); // Throw a runtime exception
        }

        return existingUser; // Return the existing user
    }

    // Read/Get User by Username
    public User getUserByUsername(String username) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User with email " + username + " not found"));
        return existingUser; // Return the existing user
    }

    // Update user
    public User updateUserByUsername(String username, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User with email " + username + " not found")); // Find the user by username

        if (!userUpdateDTO.getOldPassword().equals(existingUser.getPassword())) { // If the provided password does not match the existing user's password
            throw new RuntimeException("The provided password is incorrect"); // Throw a runtime exception
        }
        existingUser.setUsername(userUpdateDTO.getUsername()); // Set the new username
        existingUser.setEmail(userUpdateDTO.getEmail()); // Set the new email
        existingUser.setAddress(userUpdateDTO.getAddress()); // Set the new address
        existingUser.setPassword(userUpdateDTO.getNewPassword()); // Set the new password directly

        userRepository.save(existingUser); // Save the existing user
        return existingUser; // Return the existing user
    }
}
