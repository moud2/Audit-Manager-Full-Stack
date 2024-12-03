package com.insight.backend.dto;

import java.util.List;

/**
 * Data Transfer Object representing the progress and statistics of an audit.
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
     * List containing progress details for each category within the audit.
     * Each entry contains category name, category ID, answered questions count,
     * total questions count, and progress percentage.
     */
    private List<CategoryProgressDTO> categoryProgress;

    /**
     * Default constructor for AuditProgressDTO.
     * Initializes the categoryProgress list to avoid null values.
     */
    public AuditProgressDTO() {
        this.categoryProgress = List.of();
    }

    /**
     * Constructor to initialize all fields of the AuditProgressDTO.
     *
     * @param auditId          unique identifier for the audit.
     * @param overallProgress  overall progress of the audit as a percentage.
     * @param categoryProgress progress for each category in the audit.
     */
    public AuditProgressDTO(Long auditId, double overallProgress, List<CategoryProgressDTO> categoryProgress) {
        this.auditId = auditId;
        this.overallProgress = overallProgress;
        this.categoryProgress = categoryProgress != null ? categoryProgress : List.of();
    }

    /**
     * Gets the unique identifier for the audit.
     *
     * @return the audit ID.
     */
    public Long getAuditId() {
        return auditId;
    }

    /**
     * Sets the unique identifier for the audit.
     *
     * @param auditId the audit ID to set.
     */
    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    /**
     * Gets the overall progress of the audit as a percentage.
     *
     * @return the overall progress.
     */
    public double getOverallProgress() {
        return overallProgress;
    }

    /**
     * Sets the overall progress of the audit as a percentage.
     *
     * @param overallProgress the overall progress to set.
     */
    public void setOverallProgress(double overallProgress) {
        this.overallProgress = overallProgress;
    }

    /**
     * Gets the list of category progress details.
     *
     * @return the category progress details.
     */
    public List<CategoryProgressDTO> getCategoryProgress() {
        return categoryProgress;
    }

    /**
     * Sets the list of category progress details.
     *
     * @param categoryProgress the category progress details to set.
     */
    public void setCategoryProgress(List<CategoryProgressDTO> categoryProgress) {
        this.categoryProgress = categoryProgress != null ? categoryProgress : List.of();
    }
}
