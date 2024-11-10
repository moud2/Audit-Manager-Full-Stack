package com.insight.backend.service.audit;

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

    private final RatingRepository ratingRepository;

    /**
     * Constructs an AuditProgressService with the specified RatingRepository.
     *
     * @param ratingRepository the repository to retrieve rating data for an audit
     */
    @Autowired
    public AuditProgressService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Calculates the progress of an audit, including overall and per-category progress,
     * and the count of questions per rating score. Excludes questions marked as "n.A.".
     *
     * @param auditId unique identifier for the audit
     * @return an AuditProgressDTO containing the overall progress, per-category progress, and question counts by rating
     */
    public AuditProgressDTO calculateAuditProgress(Long auditId) {
        List<Rating> ratings = ratingRepository.findByAuditId(auditId);

        Map<Long, Integer> categoryPointsSum = new HashMap<>();
        Map<Long, Integer> categoryQuestionCount = new HashMap<>();
        Map<Integer, Long> questionCountByRating = new HashMap<>();

        // Calculate category and overall progress, and question counts by rating
        int totalPoints = calculateCategoryAndOverallProgress(ratings, categoryPointsSum, categoryQuestionCount, questionCountByRating);
        double overallProgress = calculateOverallProgress(totalPoints, ratings.size());

        // Calculate per-category progress percentages
        Map<Long, Double> categoryProgress = calculateCategoryProgress(categoryPointsSum, categoryQuestionCount);

        return new AuditProgressDTO(auditId, overallProgress, categoryProgress, questionCountByRating);
    }

    /**
     * Calculates the total points for all answered questions and updates category totals and rating counts.
     *
     * @param ratings               list of ratings associated with an audit
     * @param categoryPointsSum     map to store the sum of points for each category
     * @param categoryQuestionCount map to store the count of questions for each category
     * @param questionCountByRating map to store the count of questions by each rating score
     * @return the total points across all categories for answered questions
     */
    private int calculateCategoryAndOverallProgress(List<Rating> ratings, Map<Long, Integer> categoryPointsSum,
                                                    Map<Long, Integer> categoryQuestionCount, Map<Integer, Long> questionCountByRating) {
        int totalPoints = 0;
        for (Rating rating : ratings) {
            if (!rating.getNa()) {
                int points = rating.getPoints();
                Long categoryId = rating.getQuestion().getCategory().getId();

                // Update category totals
                categoryPointsSum.merge(categoryId, points, Integer::sum);
                categoryQuestionCount.merge(categoryId, 1, Integer::sum);

                // Update rating counts
                questionCountByRating.merge(points, 1L, Long::sum);

                // Update overall total points
                totalPoints += points;
            }
        }
        return totalPoints;
    }

    /**
     * Calculates the overall progress percentage based on the total points and the number of answered questions.
     *
     * @param totalPoints        total points accumulated across all categories
     * @param answeredQuestions  the number of questions that have been answered
     * @return the overall progress percentage for the audit
     */
    private double calculateOverallProgress(int totalPoints, int answeredQuestions) {
        return answeredQuestions > 0 ? (double) totalPoints / (5 * answeredQuestions) * 100 : 0;
    }

    /**
     * Calculates progress percentages for each category based on the total points and question count.
     *
     * @param categoryPointsSum     map containing the total points for each category
     * @param categoryQuestionCount map containing the count of questions for each category
     * @return a map where each category ID is associated with its respective progress percentage
     */
    private Map<Long, Double> calculateCategoryProgress(Map<Long, Integer> categoryPointsSum, Map<Long, Integer> categoryQuestionCount) {
        Map<Long, Double> categoryProgress = new HashMap<>();
        categoryPointsSum.forEach((categoryId, pointsSum) -> {
            int questionCount = categoryQuestionCount.get(categoryId);
            double progress = questionCount > 0 ? (double) pointsSum / (5 * questionCount) * 100 : 0;
            categoryProgress.put(categoryId, progress);
        });
        return categoryProgress;
    }
}
