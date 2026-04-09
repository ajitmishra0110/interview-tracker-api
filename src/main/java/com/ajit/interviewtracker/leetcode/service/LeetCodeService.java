package com.ajit.interviewtracker.leetcode.service;

import com.ajit.interviewtracker.leetcode.LeetCodeClient;
import com.ajit.interviewtracker.leetcode.dto.LeetCodeResponseDTO;
import com.ajit.interviewtracker.leetcode.entity.LeetCodeStats;
import com.ajit.interviewtracker.leetcode.repository.LeetCodeRepository;
import com.ajit.interviewtracker.user.entity.User;
import com.ajit.interviewtracker.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeetCodeService {

    @Autowired
    private LeetCodeClient client;

    @Autowired
    private LeetCodeRepository repository;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LeetCodeResponseDTO analyze(Long userId) {

        try {
            // 🔹 Fetch user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String username = user.getLeetCodeUsername();

            // 🔹 Call GraphQL API
            String response = client.fetchUserStats(username);

            JsonNode root = objectMapper.readTree(response);

            if (root.path("data").path("matchedUser").isMissingNode()
                    || root.path("data").path("matchedUser").isNull()) {
                throw new RuntimeException("Invalid LeetCode username");
            }

            JsonNode stats = root
                    .path("data")
                    .path("matchedUser")
                    .path("submitStats")
                    .path("acSubmissionNum");

            int easy = 0, medium = 0, hard = 0;

            for (JsonNode node : stats) {
                String difficulty = node.path("difficulty").asText();
                int count = node.path("count").asInt();

                switch (difficulty) {
                    case "Easy":
                        easy = count;
                        break;
                    case "Medium":
                        medium = count;
                        break;
                    case "Hard":
                        hard = count;
                        break;
                }
            }

            int total = easy + medium + hard;

            String weakness;
            if (hard < 50) {
                weakness = "Hard Problems";
            } else if (medium < easy) {
                weakness = "Medium Problems";
            } else {
                weakness = "Balanced";
            }

            // 🔹 Time-based persistence
            LeetCodeStats existing =
                    repository.findTopByUserIdOrderByLastUpdatedDesc(userId);

            if (existing == null ||
                    existing.getLastUpdated().isBefore(LocalDateTime.now().minusHours(24))) {

                LeetCodeStats statsEntity = new LeetCodeStats();
                statsEntity.setUser(user);
                statsEntity.setTotalSolved(total);
                statsEntity.setEasyCount(easy);
                statsEntity.setMediumCount(medium);
                statsEntity.setHardCount(hard);
                statsEntity.setLastUpdated(LocalDateTime.now());

                repository.save(statsEntity);
            }

            // 🔹 Progress %
            double progressPercentage = 0.0;

            List<LeetCodeStats> history =
                    repository.findTop2ByUserIdOrderByLastUpdatedDesc(userId);

            if (history.size() >= 2) {
                int latest = history.get(0).getTotalSolved();
                int previous = history.get(1).getTotalSolved();

                if (previous > 0) {
                    progressPercentage = ((latest - previous) * 100.0) / previous;
                }
            }

            // 🔹 Response
            return new LeetCodeResponseDTO(
                    username,
                    total,
                    easy,
                    medium,
                    hard,
                    weakness,
                    progressPercentage,
                    "Analysis completed successfully"
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch and process LeetCode data", e);
        }
    }

    // 📈 Paginated History
    public Page<LeetCodeStats> getHistory(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<LeetCodeStats> history =
                repository.findByUserIdOrderByLastUpdatedDesc(userId, pageable);

        if (history.isEmpty()) {
            throw new RuntimeException("No history found for userId: " + userId);
        }

        return history;
    }

    // 🏆 Leaderboard
    public List<LeetCodeStats> getLeaderboard(int limit) {

        List<LeetCodeStats> latestStats =
                repository.findLatestStatsForAllUsers();

        return latestStats.stream()
                .sorted((a, b) -> Integer.compare(b.getTotalSolved(), a.getTotalSolved()))
                .limit(limit)
                .toList();
    }
}