package com.insight.backend.dto;

/**
 * Data Transfer Object for representing progress details for a category.
 */
public class CategoryProgressDTO {

    private Long categoryId;
    private String categoryName;
    private int answeredQuestions;
    private int totalQuestions;
    private double currentCategoryProgress;

    /**
     * Constructor to initialize all fields of the CategoryProgressDTO.
     *
     * @param categoryId              unique identifier for the category.
     * @param categoryName            name of the category.
     * @param answeredQuestions       number of questions answered in the category.
     * @param totalQuestions          total number of questions in the category.
     * @param currentCategoryProgress progress percentage for answered questions.
     */
    public CategoryProgressDTO(Long categoryId, String categoryName, int answeredQuestions, int totalQuestions, double currentCategoryProgress) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.answeredQuestions = answeredQuestions;
        this.totalQuestions = totalQuestions;
        this.currentCategoryProgress = currentCategoryProgress;
    }

    /**
     * Gets the unique identifier for the category.
     *
     * @return the category ID.
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the unique identifier for the category.
     *
     * @param categoryId the category ID to set.
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the name of the category.
     *
     * @return the category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName the category name to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the number of answered questions in the category.
     *
     * @return the count of answered questions.
     */
    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    /**
     * Sets the number of answered questions in the category.
     *
     * @param answeredQuestions the count of answered questions to set.
     */
    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    /**
     * Gets the total number of questions in the category.
     *
     * @return the total question count.
     */
    public int getTotalQuestions() {
        return totalQuestions;
    }

    /**
     * Sets the total number of questions in the category.
     *
     * @param totalQuestions the total question count to set.
     */
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    /**
     * Gets the progress percentage for answered questions.
     *
     * @return the current category progress percentage.
     */
    public double getCurrentCategoryProgress() {
        return currentCategoryProgress;
    }

    /**
     * Sets the progress percentage for answered questions.
     *
     * @param currentCategoryProgress the current category progress percentage to set.
     */
    public void setCurrentCategoryProgress(double currentCategoryProgress) {
        this.currentCategoryProgress = currentCategoryProgress;
    }

}
