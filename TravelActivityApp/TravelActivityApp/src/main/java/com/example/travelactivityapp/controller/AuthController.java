package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.service.AuthService;
import com.example.travelactivityapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User createdUser = userService.signup(user);
        return ResponseEntity.ok(createdUser);
    }

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
