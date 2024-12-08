package com.insight.backend.dto;

import java.util.List;

/**
 * Data Transfer Object representing the progress and statistics of an audit.
 */
public class AuditProgressDTO {

    private Long auditId;
    private double currentAuditProgress;
    private List<CategoryProgressDTO> categoryProgress;

    /**
     * Constructor to initialize all fields of the AuditProgressDTO.
     *
     * @param auditId              unique identifier for the audit.
     * @param currentAuditProgress progress percentage for answered questions.
     * @param overallAuditProgress progress percentage for all questions.
     * @param categoryProgress     progress details for each category.
     */
    public AuditProgressDTO(Long auditId, double currentAuditProgress, List<CategoryProgressDTO> categoryProgress) {
        this.auditId = auditId;
        this.currentAuditProgress = currentAuditProgress;
        this.categoryProgress = categoryProgress;
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
     * Gets the progress percentage for answered questions.
     *
     * @return the current audit progress percentage.
     */
    public double getCurrentAuditProgress() {
        return currentAuditProgress;
    }

    /**
     * Sets the progress percentage for answered questions.
     *
     * @param currentAuditProgress the current audit progress percentage to set.
     */
    public void setCurrentAuditProgress(double currentAuditProgress) {
        this.currentAuditProgress = currentAuditProgress;
    }


    /**
     * Gets the progress details for each category.
     *
     * @return the list of category progress details.
     */
    public List<CategoryProgressDTO> getCategoryProgress() {
        return categoryProgress;
    }

    /**
     * Sets the progress details for each category.
     *
     * @param categoryProgress the list of category progress details to set.
     */
    public void setCategoryProgress(List<CategoryProgressDTO> categoryProgress) {
        this.categoryProgress = categoryProgress;
    }
}
