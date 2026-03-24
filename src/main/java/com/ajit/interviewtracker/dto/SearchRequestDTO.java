package com.ajit.interviewtracker.dto;

import lombok.Data;

@Data
public class SearchRequestDTO {

    private String topic;
    private String difficulty;
    private int page = 0;
    private int size = 5;
}