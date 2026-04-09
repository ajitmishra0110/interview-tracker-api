package com.ajit.interviewtracker.user.controller;

import com.ajit.interviewtracker.user.entity.User;
import com.ajit.interviewtracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Create User
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        if (user.getLeetCodeUsername() == null || user.getLeetCodeUsername().isEmpty()) {
            throw new RuntimeException("LeetCode username is required");
        }

        user.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(userRepository.save(user));
    }

    // ✅ Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }

    // ✅ Get All Users (optional but useful)
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}