package com.ajit.interviewtracker.leetcode.entity;

import com.ajit.interviewtracker.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "leetcode_stats",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_last_updated", columnList = "last_updated")
        }
)
public class LeetCodeStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int totalSolved;
    private int easyCount;
    private int mediumCount;
    private int hardCount;

    private LocalDateTime lastUpdated;

}