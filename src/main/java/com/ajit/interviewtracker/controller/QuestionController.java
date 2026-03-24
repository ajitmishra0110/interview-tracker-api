package com.ajit.interviewtracker.controller;

import com.ajit.interviewtracker.dto.DailyStatsDTO;
import com.ajit.interviewtracker.dto.SearchRequestDTO;
import com.ajit.interviewtracker.dto.TopicStatsDTO;
import com.ajit.interviewtracker.entity.Question;
import com.ajit.interviewtracker.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return service.addQuestion(question);
    }

    @GetMapping("/stats/topics")
    public List<TopicStatsDTO> getTopicStats() {
        return service.getTopicStats();
    }

    @GetMapping("/stats/weak-topics")
    public List<TopicStatsDTO> getWeakTopics() {
        return service.getWeakTopics();
    }

    @GetMapping("/stats/daily")
    public List<DailyStatsDTO> getDailyStats() {
        return service.getDailyStats();
    }

    @PostMapping("/search")
    public Page<Question> searchQuestions(@RequestBody SearchRequestDTO request) {
        return service.searchQuestions(request);
    }
}