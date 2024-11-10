package com.insight.backend.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object representing the progress and rating summary of an audit.
 * This object is used in the response for audit progress endpoints.
 */
public class AuditProgressDTO {

    /**
     * Unique identifier for the audit.
     */
    private Long auditId;

    /**
     * Overall progress of the audit as a percentage.
     * Calculated based on the total points for all questions within the audit.
     */
    private double overallProgress;

    /**
     * Map containing progress for each category within the audit.
     * The map's keys are category names, and the values are progress percentages.
     */
    private Map<String, Double> categoryProgress;

    /**
     * Map representing the count of questions by their rating.
     * The keys represent rating values (0-5, nA), and the values are the counts for each rating.
     */
    private Map<String, Long> questionCountByRating;

    /**
     * Default constructor for AuditProgressDTO.
     * Initializes the Map fields to avoid null values.
     */
    public AuditProgressDTO() {
        this.categoryProgress = new HashMap<>();
        this.questionCountByRating = new HashMap<>();
        initializeDefaultRatings();
    }

    /**
     * Constructor to initialize all fields of the AuditProgressDTO.
     *
     * @param auditId             unique identifier for the audit.
     * @param overallProgress     overall progress of the audit as a percentage.
     * @param categoryProgress    progress for each category in the audit, represented as a map.
     * @param questionCountByRating the count of questions by their rating, represented as a map.
     */
    public AuditProgressDTO(Long auditId, double overallProgress, Map<String, Double> categoryProgress, Map<String, Long> questionCountByRating) {
        this.auditId = auditId;
        this.overallProgress = overallProgress;
        this.categoryProgress = categoryProgress != null ? categoryProgress : new HashMap<>();
        this.questionCountByRating = questionCountByRating != null ? questionCountByRating : new HashMap<>();
        initializeDefaultRatings();
    }

    /**
     * Initializes default rating values (0-5, nA) to 0 if they are not already set.
     */
    private void initializeDefaultRatings() {
        for (int i = 0; i <= 5; i++) {
            this.questionCountByRating.putIfAbsent(String.valueOf(i), 0L);
        }
        this.questionCountByRating.putIfAbsent("nA", 0L);
    }

    /**
     * Getter method for audit ID.
     *
     * @return the unique identifier for the audit.
     */
    public Long getAuditId() {
        return auditId;
    }

    /**
     * Setter method for audit ID.
     *
     * @param auditId the unique identifier for the audit.
     */
    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    /**
     * Getter method for overall progress.
     *
     * @return the overall progress of the audit as a percentage.
     */
    public double getOverallProgress() {
        return overallProgress;
    }

    /**
     * Setter method for overall progress.
     *
     * @param overallProgress the overall progress of the audit as a percentage.
     */
    public void setOverallProgress(double overallProgress) {
        this.overallProgress = overallProgress;
    }

    /**
     * Getter method for category progress.
     *
     * @return a map of category IDs to their respective progress percentages.
     */
    public Map<String, Double> getCategoryProgress() {
        return categoryProgress;
    }

    /**
     * Setter method for category progress.
     * Ensures a non-null value by initializing an empty map if null is passed.
     *
     * @param categoryProgress a map of category IDs to their respective progress percentages.
     */
    public void setCategoryProgress(Map<String, Double> categoryProgress) {
        this.categoryProgress = categoryProgress != null ? categoryProgress : new HashMap<>();
    }

    /**
     * Getter method for question count by rating.
     *
     * @return a map of rating values (0-5) to their respective question counts.
     */
    public Map<String, Long> getQuestionCountByRating() {
        return questionCountByRating;
    }

    /**
     * Setter method for question count by rating.
     * Ensures a non-null value by initializing an empty map if null is passed.
     *
     * @param questionCountByRating a map of rating values (0-5) to their respective question counts.
     */
    public void setQuestionCountByRating(Map<String, Long> questionCountByRating) {
        this.questionCountByRating = questionCountByRating != null ? questionCountByRating : new HashMap<>();
    }
}
