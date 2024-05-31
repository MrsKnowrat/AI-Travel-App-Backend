package com.example.travelactivityapp.controller;

/* This class handles  */

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.UserUpdateDTO;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.service.UserService;
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
@RequestMapping("/users") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class UserController {

    @Autowired // Handles business logic for user ops
    private UserService userService;

    // Get User By Username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@Valid @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        CommonResponse response = CommonResponse.builder().hasError(false).data(user).message("User Created successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    // Update User
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@Valid @PathVariable String username, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedUser = userService.updateUserByUsername(username, userUpdateDTO);
        CommonResponse response = CommonResponse.builder().hasError(false).data(updatedUser).message("User updated successfully").status(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

    // Delete User & Profile - Coming soon!
}
