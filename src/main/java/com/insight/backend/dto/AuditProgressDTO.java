package com.insight.backend.dto;

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
     * The map's keys are category IDs, and the values are progress percentages.
     */
    private Map<Long, Double> categoryProgress;

    /**
     * Map representing the count of questions by their rating.
     * The keys represent rating values (0-5), and the values are the counts for each rating.
     */
    private Map<Integer, Long> questionCountByRating;

    /**
     * Default constructor for AuditProgressDTO.
     */
    public AuditProgressDTO() {
    }

    /**
     * Constructor to initialize all fields of the AuditProgressDTO.
     *
     * @param auditId             unique identifier for the audit.
     * @param overallProgress     overall progress of the audit as a percentage.
     * @param categoryProgress    progress for each category in the audit, represented as a map.
     * @param questionCountByRating the count of questions by their rating, represented as a map.
     */
    public AuditProgressDTO(Long auditId, double overallProgress, Map<Long, Double> categoryProgress, Map<Integer, Long> questionCountByRating) {
        this.auditId = auditId;
        this.overallProgress = overallProgress;
        this.categoryProgress = categoryProgress;
        this.questionCountByRating = questionCountByRating;
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
    public Map<Long, Double> getCategoryProgress() {
        return categoryProgress;
    }

    /**
     * Setter method for category progress.
     *
     * @param categoryProgress a map of category IDs to their respective progress percentages.
     */
    public void setCategoryProgress(Map<Long, Double> categoryProgress) {
        this.categoryProgress = categoryProgress;
    }

    /**
     * Getter method for question count by rating.
     *
     * @return a map of rating values (0-5) to their respective question counts.
     */
    public Map<Integer, Long> getQuestionCountByRating() {
        return questionCountByRating;
    }

    /**
     * Setter method for question count by rating.
     *
     * @param questionCountByRating a map of rating values (0-5) to their respective question counts.
     */
    public void setQuestionCountByRating(Map<Integer, Long> questionCountByRating) {
        this.questionCountByRating = questionCountByRating;
    }
}
