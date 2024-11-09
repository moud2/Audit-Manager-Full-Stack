package com.insight.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insight.backend.dto.AuditProgressDTO;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service responsible for audit-specific operations.
 * Calculates the progress and statistics for an audit, based on the ratings of questions within categories.
 */
@Service
public class AuditProgressService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Calculates the progress of an audit, including overall and per-category progress,
     * and the count of questions per rating score. Excludes questions marked as "n.A.".
     *
     * @param auditId unique identifier for the audit.
     * @return an AuditProgressDTO containing the overall progress, per-category progress, and question counts by rating.
     */
    public AuditProgressDTO calculateAuditProgress(Long auditId) {
        List<Rating> ratings = ratingRepository.findByAuditId(auditId);

        Map<Long, Integer> categoryPointsSum = new HashMap<>();
        Map<Long, Integer> categoryQuestionCount = new HashMap<>();
        Map<Integer, Long> questionCountByRating = new HashMap<>();
        int totalPoints = 0;
        int answeredQuestions = 0;

        // Loop through ratings to compute totals for each category and overall progress.
        for (Rating rating : ratings) {
            if (!rating.getNa()) {
                int points = rating.getPoints();
                Long categoryId = rating.getQuestion().getCategory().getId();

                // Update category totals
                categoryPointsSum.merge(categoryId, points, Integer::sum);
                categoryQuestionCount.merge(categoryId, 1, Integer::sum);

                // Update rating counts
                questionCountByRating.merge(points, 1L, Long::sum);

                // Update overall totals
                totalPoints += points;
                answeredQuestions++;
            }
        }

        // Calculate category progress percentages
        Map<Long, Double> categoryProgress = new HashMap<>();
        categoryPointsSum.forEach((categoryId, pointsSum) -> {
            int questionCount = categoryQuestionCount.get(categoryId);
            double progress = questionCount > 0 ? (double) pointsSum / (5 * questionCount) * 100 : 0;
            categoryProgress.put(categoryId, progress);
        });

        // Calculate overall progress percentage
        double overallProgress = answeredQuestions > 0 ? (double) totalPoints / (5 * answeredQuestions) * 100 : 0;

        // Construct the DTO with calculated progress and statistics
        AuditProgressDTO dto = new AuditProgressDTO();
        dto.setAuditId(auditId);
        dto.setOverallProgress(overallProgress);
        dto.setCategoryProgress(categoryProgress);
        dto.setQuestionCountByRating(questionCountByRating);

        return dto;
    }
}
