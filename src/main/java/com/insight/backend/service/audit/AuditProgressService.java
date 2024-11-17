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

        Map<String, Integer> categoryPointsSum = new HashMap<>();
        Map<String, Integer> categoryQuestionCount = new HashMap<>();
        Map<String, Long> questionCountByRating = initializeDefaultRatings();

        // Calculate category and overall progress, and question counts by rating
        int totalPoints = calculateCategoryAndOverallProgress(ratings, categoryPointsSum, categoryQuestionCount, questionCountByRating);
        int answeredQuestions = (int) ratings.stream().filter(rating -> !rating.getNa()).count(); // Ignoriere "n.A."-Bewertungen
        double overallProgress = calculateOverallProgress(totalPoints, answeredQuestions);

        // Calculate per-category progress percentages
        Map<String, Double> categoryProgress = calculateCategoryProgress(categoryPointsSum, categoryQuestionCount);

        return new AuditProgressDTO(auditId, overallProgress, categoryProgress, questionCountByRating);
    }

    /**
     * Initializes the questionCountByRating map with default values for ratings 0-5 and "nA".
     *
     * @return a map initialized with default rating values all set to 0
     */
    private Map<String, Long> initializeDefaultRatings() {
        Map<String, Long> questionCountByRating = new HashMap<>();
        for (int i = 0; i <= 5; i++) {
            questionCountByRating.put(String.valueOf(i), 0L);
        }
        questionCountByRating.put("nA", 0L);
        return questionCountByRating;
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
    private int calculateCategoryAndOverallProgress(List<Rating> ratings, Map<String, Integer> categoryPointsSum,
                                                    Map<String, Integer> categoryQuestionCount, Map<String, Long> questionCountByRating) {
        int totalPoints = 0;
        for (Rating rating : ratings) {
            if (!rating.getNa()) {
                int points = rating.getPoints();
                String categoryName = rating.getQuestion().getCategory().getName();

                // Update category totals
                categoryPointsSum.merge(categoryName, points, Integer::sum);
                categoryQuestionCount.merge(categoryName, 1, Integer::sum);

                // Update rating counts
                questionCountByRating.merge(String.valueOf(points), 1L, Long::sum);

                // Update overall total points
                totalPoints += points;
            } else {
                questionCountByRating.merge("nA", 1L, Long::sum);
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
     * @return a map where each category name is associated with its respective progress percentage
     */
    private Map<String, Double> calculateCategoryProgress(Map<String, Integer> categoryPointsSum, Map<String, Integer> categoryQuestionCount) {
        Map<String, Double> categoryProgress = new HashMap<>();
        categoryPointsSum.forEach((categoryName, pointsSum) -> {
            int questionCount = categoryQuestionCount.get(categoryName);
            double progress = questionCount > 0 ? (double) pointsSum / (5 * questionCount) * 100 : 0;
            categoryProgress.put(categoryName, progress);
        });
        return categoryProgress;
    }
}
