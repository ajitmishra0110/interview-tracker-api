package com.ajit.interviewtracker.leetcode.scheduler;

import com.ajit.interviewtracker.leetcode.service.LeetCodeService;
import com.ajit.interviewtracker.user.entity.User;
import com.ajit.interviewtracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeetCodeScheduler {

    @Autowired
    private LeetCodeService service;

    @Autowired
    private UserRepository userRepository;

    // 🕒 Runs every minute (for testing)
    // Change to: "0 0 12 * * ?" for production
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateAllUsersStats() {

        System.out.println("Scheduler started...");

        // 🔹 Get all users
        List<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                service.analyze(user.getId());
                System.out.println("Updated stats for userId: " + user.getId());
            } catch (Exception e) {
                System.out.println("Failed for userId: " + user.getId());
            }
        }

        System.out.println("Scheduler finished.");
    }
}