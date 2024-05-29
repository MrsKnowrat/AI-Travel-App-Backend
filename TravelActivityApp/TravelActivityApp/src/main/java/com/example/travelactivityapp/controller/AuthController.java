package com.example.travelactivityapp.controller;

// This class manages the creation of users, removing the need for a separate create user endpoint in UserController.

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.UserLoginDTO;
import com.example.travelactivityapp.dto.UserRegistrationDTO;
import com.example.travelactivityapp.model.Profile;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@CrossOrigin(origins = "*") // Helps backend & frontend can communicate
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    ModelMapperUtil modelMapperUtil;

    /* CRUD
    C- Included here: handled by AuthController & UserService signup and login methods
    R- Not necessary here
    U- Not necessary here
    D- Not necessary here
    */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            User user = userService.registerUser(userRegistrationDTO);
            CommonResponse response = CommonResponse.builder()
                .hasError(false)
                .data(user)
                .message("User and profile created successfully")
                .status(HttpStatus.OK)
                .build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            CommonResponse response = CommonResponse.builder()
                    .hasError(true)
                    .error(e.getMessage())
                    .message("Failed to create user")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.loginUser(userLoginDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(user).message("User authenticated successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }
}

