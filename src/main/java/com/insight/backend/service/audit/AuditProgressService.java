package com.insight.backend.service.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insight.backend.dto.AuditProgressDTO;
import com.insight.backend.dto.CategoryProgressDTO;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for audit-specific operations.
 * Calculates progress and statistics for an audit based on the ratings of questions within categories.
 */
@Service
public class AuditProgressService {

    private final RatingRepository ratingRepository;

    /**
     * Constructs an AuditProgressService with the specified RatingRepository.
     *
     * @param ratingRepository the repository to retrieve rating data for an audit.
     */
    @Autowired
    public AuditProgressService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Calculates the progress of an audit, including both overall and per-category progress.
     * Includes progress breakdown for answered and total questions.
     *
     * @param auditId unique identifier for the audit.
     * @return an AuditProgressDTO containing progress details.
     */
    public AuditProgressDTO calculateAuditProgress(Long auditId) {
        List<Rating> ratings = ratingRepository.findByAuditId(auditId);

        Map<Long, String> categoryNames = new HashMap<>();
        Map<Long, Integer> totalQuestionsPerCategory = new HashMap<>();
        Map<Long, Integer> answeredQuestionsPerCategory = new HashMap<>();
        Map<Long, Integer> pointsPerCategory = new HashMap<>();

        int totalPoints = 0;
        int answeredQuestions = 0;
        int totalQuestions = 0;

        // Iterate through ratings to calculate category-specific metrics
        for (Rating rating : ratings) {
            Long categoryId = rating.getQuestion().getCategory().getId();
            String categoryName = rating.getQuestion().getCategory().getName();

            categoryNames.putIfAbsent(categoryId, categoryName);
            totalQuestionsPerCategory.merge(categoryId, 1, Integer::sum);
            totalQuestions++;

            if (!rating.getNa()) {
                int points = rating.getPoints() != null ? rating.getPoints() : 0;
                pointsPerCategory.merge(categoryId, points, Integer::sum);
                answeredQuestionsPerCategory.merge(categoryId, 1, Integer::sum);
                totalPoints += points;
                answeredQuestions++;
            }
        }

        // Build category progress DTOs
        List<CategoryProgressDTO> categoryProgressList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : categoryNames.entrySet()) {
            Long categoryId = entry.getKey();
            String categoryName = entry.getValue();

            int categoryTotalQuestions = totalQuestionsPerCategory.getOrDefault(categoryId, 0);
            int categoryAnsweredQuestions = answeredQuestionsPerCategory.getOrDefault(categoryId, 0);
            int categoryPoints = pointsPerCategory.getOrDefault(categoryId, 0);

            double currentCategoryProgress = categoryAnsweredQuestions > 0
                    ? (double) categoryPoints / (5 * categoryAnsweredQuestions) * 100
                    : 0;


            categoryProgressList.add(new CategoryProgressDTO(
                    categoryId,
                    categoryName,
                    categoryAnsweredQuestions,
                    categoryTotalQuestions,
                    currentCategoryProgress,
            ));
        }

        // Calculate progress metrics
        double currentAuditProgress = answeredQuestions > 0
                ? (double) totalPoints / (5 * answeredQuestions) * 100
                : 0;

        return new AuditProgressDTO(auditId, currentAuditProgress, categoryProgressList);
    }
}
