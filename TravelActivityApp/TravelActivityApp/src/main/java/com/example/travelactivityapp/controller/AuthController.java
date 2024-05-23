package com.example.travelactivityapp.controller;

// This class manages the creation of users, removing the need for a separate create user endpoint in UserController.

import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins =  "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // check on 5/22's code discussion to make sure hashmap is used to sign up
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User createdUser = authService.signup(user);
        return ResponseEntity.ok(createdUser);
    }

    // make sure code includes LoginDTO and ResponseDTO
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isAuthenticated = authService.login(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
