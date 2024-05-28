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

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    IUserRepository userRepository; // dependency injection.

    @Autowired
    ModelMapperUtil modelMapperUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IProfileRepository profileRepository;

    private ModelMapper modelMapper;

    @Autowired
    public UserService(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        try {
            User newUser = modelMapperUtil.map(userRegistrationDTO, User.class);
            String rawPassword = newUser.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);

            newUser.setPassword(encodedPassword);
            return userRepository.save(newUser);
        } catch (Exception e) {
            if (e.getMessage().contains("violates unique constraint")) {
                throw new RuntimeException("Username or email you provided already exists");
            }
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public User loginUser(UserLoginDTO userLoginDTO) {
        User existingUser = userRepository.findUserByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new RuntimeException("User with email " + userLoginDTO.getEmail() + " not found"));
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
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

        if(!passwordEncoder.matches(userUpdateDTO.getOldPassword(), existingUser.getPassword())) {
            throw new RuntimeException("The provided password is incorrect");
        }
        existingUser.setUsername(userUpdateDTO.getUsername());
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setAddress(userUpdateDTO.getAddress());
        existingUser.setPassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));

        userRepository.save(existingUser);
        return existingUser;
    }

    // No need to include User delete method, as it will be deleted of the User deletes their profile
}
