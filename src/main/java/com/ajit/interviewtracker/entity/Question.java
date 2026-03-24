package com.ajit.interviewtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String platform; // LeetCode, GFG

    private String difficulty; // EASY, MEDIUM, HARD

    private String topic; // DP, Graph, etc.

    private LocalDate solvedDate;

    private Integer timeTaken; // in minutes
}