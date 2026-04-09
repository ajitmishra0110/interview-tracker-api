package com.ajit.interviewtracker.leetcode.repository;

import com.ajit.interviewtracker.leetcode.entity.LeetCodeStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeetCodeRepository extends JpaRepository<LeetCodeStats, Long> {

    // ✅ Latest record
    LeetCodeStats findTopByUserIdOrderByLastUpdatedDesc(Long userId);

    // ✅ Top 2 for progress
    List<LeetCodeStats> findTop2ByUserIdOrderByLastUpdatedDesc(Long userId);

    // ✅ History
    List<LeetCodeStats> findByUserIdOrderByLastUpdatedDesc(Long userId);

    // ✅ Paginated history
    Page<LeetCodeStats> findByUserIdOrderByLastUpdatedDesc(Long userId, Pageable pageable);

    // ✅ Leaderboard (keep your existing query)
    @Query(value = """
        SELECT DISTINCT ON (user_id) *
        FROM leetcode_stats
        ORDER BY user_id, last_updated DESC
    """, nativeQuery = true)
    List<LeetCodeStats> findLatestStatsForAllUsers();
}