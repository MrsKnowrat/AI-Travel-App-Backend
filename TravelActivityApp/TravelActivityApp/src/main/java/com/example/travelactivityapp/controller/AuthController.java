package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.service.AuthService;
import com.example.travelactivityapp.service.UserService;
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

    // check on today's code discussion to make sure hashmap is used to sign up
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User createdUser = userService.signup(user);
        return ResponseEntity.ok(createdUser);
    }

    // make sure code includes LoginDTO and ResponseDTO
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isAuthenticated = userService.login(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
