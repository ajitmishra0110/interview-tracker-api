package com.ajit.interviewtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicStatsDTO {

    private String topic;
    private Long count;
}