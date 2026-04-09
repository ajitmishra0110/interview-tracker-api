package com.ajit.interviewtracker.leetcode.dto;

public class LeetCodeResponseDTO {

    private String username;
    private int totalSolved;
    private int easy;
    private int medium;
    private int hard;
    private String weakness;
    private double progressPercentage;
    private String message;

    public LeetCodeResponseDTO(String username, int totalSolved, int easy, int medium, int hard, String weakness, double progressPercentage, String message) {
        this.username = username;
        this.totalSolved = totalSolved;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.weakness = weakness;
        this.progressPercentage = progressPercentage;
        this.message = message;
    }

    public String getUsername() { return username; }
    public int getTotalSolved() { return totalSolved; }
    public int getEasy() { return easy; }
    public int getMedium() { return medium; }
    public int getHard() { return hard; }
    public String getWeakness() { return weakness; }
    public double getProgressPercentage() { return progressPercentage; }
    public String getMessage() { return message; }
}