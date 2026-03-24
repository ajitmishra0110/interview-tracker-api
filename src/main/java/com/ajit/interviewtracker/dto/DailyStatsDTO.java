package com.ajit.interviewtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyStatsDTO {
    private LocalDate date;
    private Long count;
}