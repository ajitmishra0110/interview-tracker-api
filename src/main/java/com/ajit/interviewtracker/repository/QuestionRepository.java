package com.ajit.interviewtracker.repository;

import com.ajit.interviewtracker.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
    SELECT q FROM Question q
    WHERE (:topic IS NULL OR q.topic = :topic)
    AND (:difficulty IS NULL OR q.difficulty = :difficulty)
    """)
    Page<Question> searchQuestions(String topic, String difficulty, Pageable pageable);

    @Query("SELECT q.topic, COUNT(q) FROM Question q GROUP BY q.topic")
    List<Object[]> countByTopic();

    @Query("SELECT q.solvedDate, COUNT(q) FROM Question q GROUP BY q.solvedDate ORDER BY q.solvedDate")
    List<Object[]> countByDate();
}