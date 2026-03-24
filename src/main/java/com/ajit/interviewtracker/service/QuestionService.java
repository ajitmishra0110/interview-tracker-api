package com.ajit.interviewtracker.service;

import com.ajit.interviewtracker.dto.DailyStatsDTO;
import com.ajit.interviewtracker.dto.SearchRequestDTO;
import com.ajit.interviewtracker.dto.TopicStatsDTO;
import com.ajit.interviewtracker.entity.Question;
import com.ajit.interviewtracker.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository repository;

    public Question addQuestion(Question question) {
        return repository.save(question);
    }

    public List<TopicStatsDTO> getTopicStats() {
        return repository.countByTopic()
                .stream()
                .map(obj -> new TopicStatsDTO(
                        (String) obj[0],
                        (Long) obj[1]
                ))
                .collect(Collectors.toList());
    }

    public List<TopicStatsDTO> getWeakTopics() {

        List<TopicStatsDTO> stats = repository.countByTopic()
                .stream()
                .map(obj -> new TopicStatsDTO(
                        (String) obj[0],
                        (Long) obj[1]
                ))
                .toList();

        // Find minimum count
        long min = stats.stream()
                .mapToLong(TopicStatsDTO::getCount)
                .min()
                .orElse(0);

        // Return only weakest topics
        return stats.stream()
                .filter(s -> s.getCount() == min)
                .toList();
    }

    public List<DailyStatsDTO> getDailyStats() {
        return repository.countByDate()
                .stream()
                .map(obj -> new DailyStatsDTO(
                        (LocalDate) obj[0],
                        (Long) obj[1]
                ))
                .toList();
    }


    public Page<Question> searchQuestions(SearchRequestDTO request) {

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("solvedDate").descending()
        );

        return repository.searchQuestions(
                request.getTopic(),
                request.getDifficulty(),
                pageable
        );
    }
}