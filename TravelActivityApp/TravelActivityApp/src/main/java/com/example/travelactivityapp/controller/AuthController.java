package com.example.travelactivityapp.controller;

// This class manages the creation of users, removing the need for a separate create user endpoint in UserController.

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.UserLoginDTO;
import com.example.travelactivityapp.dto.UserRegistrationDTO;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@CrossOrigin(origins = "*") // Helps backend & frontend can communicate
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        System.out.println(userRegistrationDTO);
        User user = userService.registerUser(userRegistrationDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(user).message("User created successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.loginUser(userLoginDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(user).message("User authenticated successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }
}
