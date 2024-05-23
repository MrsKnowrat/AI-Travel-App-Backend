package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IUserRepository;
import com.example.travelactivityapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create bulk list of user for testing purposes - include code with hashmap

    // Create new User - Sign up
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.status(409).body("Username already in use");
        } else {
            User createdUser = userService.signup(user);
            return ResponseEntity.ok(createdUser);
        }
    }
    // Read/Get User - Login
    // Checks if user exists, and if so, sends back a status of OK and username.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isAuthenticated = userService.login(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok(user.getUsername());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}
