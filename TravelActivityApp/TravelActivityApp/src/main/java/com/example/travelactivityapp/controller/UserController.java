package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.dto.CommonResponse;
import com.example.travelactivityapp.dto.UserUpdateDTO;
import com.example.travelactivityapp.model.User;
import com.example.travelactivityapp.repository.IUserRepository;
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
@RequestMapping("/users")
@Validated
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Create bulk list of user for testing purposes - include code with hashmap
    // CRUD - C- done by sign-in R- done here U- done here / D- Isn't included here
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
}
