package com.ajit.interviewtracker.leetcode.controller;

import com.ajit.interviewtracker.leetcode.service.LeetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leetcode")
public class LeetCodeController {

    @Autowired
    private LeetCodeService service;

    // ✅ Analyze user by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAnalysis(@PathVariable Long userId) {
        return ResponseEntity.ok(service.analyze(userId));
    }

    // ✅ Paginated history
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<?> getHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(service.getHistory(userId, page, size));
    }

    // ✅ Leaderboard
    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard(
            @RequestParam(defaultValue = "5") int limit) {

        return ResponseEntity.ok(service.getLeaderboard(limit));
    }
}