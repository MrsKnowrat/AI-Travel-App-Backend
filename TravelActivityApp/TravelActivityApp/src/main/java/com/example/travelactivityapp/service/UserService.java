package com.example.travelactivityapp.service;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    IUserRepository userRepository; // dependency injection.

    @Autowired
    ModelMapperUtil modelMapperUtil;

    @Autowired
    IProfileRepository profileRepository;

    /* CRUD
    C- Included here: handled by AuthController & UserService signup & login methods
    R- Included here
    U- Included here
    D- Not necessary here, as it will be deleted if the User deletes their profile
    */
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
            if (e.getMessage().contains("Violates unique constraint")) {
                throw new RuntimeException("Username or email you provided already exists");
            }
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public User loginUser(UserLoginDTO userLoginDTO) {
        User existingUser = userRepository.findUserByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new RuntimeException("User with email " + userLoginDTO.getEmail() + " not found"));
        if (!userLoginDTO.getPassword().equals(existingUser.getPassword())) {
            throw new RuntimeException("The provided password is incorrect");
        }

        return existingUser;
    }

    // Read/Get User by Username
    public User getUserByUsername(String username) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User with email " + username + " not found"));
        return existingUser;
    }

    // Update user
    public User updateUserByUsername(String username, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User with email " + username + " not found"));

        if (!userUpdateDTO.getOldPassword().equals(existingUser.getPassword())) {
            throw new RuntimeException("The provided password is incorrect");
        }
        existingUser.setUsername(userUpdateDTO.getUsername());
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setAddress(userUpdateDTO.getAddress());
        existingUser.setPassword(userUpdateDTO.getNewPassword()); // Set the new password directly

        userRepository.save(existingUser);
        return existingUser;
    }
}
