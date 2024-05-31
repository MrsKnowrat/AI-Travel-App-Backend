package com.example.travelactivityapp.controller;

/* This class handles authentication-related ops, including user registration
 and login of existing users, providing endpoints. Accordingly, createUser or loginUser
 is not needed in the UserController. */

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.UserLoginDTO;
import com.example.travelactivityapp.dto.UserRegistrationDTO;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IUserRepository;
import com.example.travelactivityapp.service.ProfileService;
import com.example.travelactivityapp.service.UserService;
import com.example.travelactivityapp.util.ModelMapperUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j // Enables logging within the class
@RestController // Marks class as controller; methods return domain object
@Validated // Ensures beans are validated before processing
@CrossOrigin(origins = "*") // Allows c/o requests from all domains
@RequestMapping("/auth") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class AuthController {

    @Autowired // Handles business logic for user ops
    UserService userService;

    @Autowired // For direct repository access
    IUserRepository userRepository;

    @Autowired // Handles business logic for user profiles
    ProfileService profileService;

    @Autowired // For object mapping
    ModelMapperUtil modelMapperUtil;

    // User Registration/Signup
    @PostMapping("/signup")
    // Method accepts regDTO object to enforce validation constraints within DTO
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            User user = userService.registerUser(userRegistrationDTO); // calls registerUser to process reg data
            CommonResponse response = CommonResponse.builder() // builds CR object
                .hasError(false)
                .data(user)
                .message("User and profile created successfully")
                .status(HttpStatus.OK)
                .build(); // response object includes new user data w/success msg
            return ResponseEntity.ok(response); // returns RE with CR object re success status
        } catch (RuntimeException e) { // RE thrown if issue during reg
            CommonResponse response = CommonResponse.builder()
                    .hasError(true)
                    .error(e.getMessage())
                    .message("Failed to create user")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.badRequest().body(response); // RE w/CR and bad HTTP status
        }
    }

    // Existing User Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            User user = userService.loginUser(userLoginDTO);
            CommonResponse response = CommonResponse.builder()
                    .hasError(false)
                    .data(user)
                    .message("You are logged in! Let's go!")
                    .status(HttpStatus.OK)
                    .build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            CommonResponse response = CommonResponse.builder()
                    .hasError(true)
                    .error(e.getMessage())
                    .message("Authentication failed. Please try again.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}

