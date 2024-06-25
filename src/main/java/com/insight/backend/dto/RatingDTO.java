package com.insight.backend.dto;

import jakarta.validation.constraints.*;

import com.insight.backend.model.Category;

/**
 * Data Transfer Object equivalent to JSON object of GET /api/v1/audits/{id}/ratings.
 */
public class RatingDTO {

    /**
     * Unique identifier for the rating.
     */
    private int id;

    /**
     * Category the rating is in.
     */
    private Category category;

    /**
     * The question being rated.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Question cannot be blank")
    @Size(max = 255, message = "Question should be up to 255 characters")
    private String question;

    /**
     * Evaluation of  Rating.
     * Range from 0 to 5.
     */
    @Min(value = 0, message = "Points can't be less than 0")
    @Max(value = 5, message = "Points can't be more than 5")
    private int points;

    /**
     * Comment on rating.
     * Must not exceed 255 characters.
     */
    @Size(max = 255, message = "Comment should be up to 255 characters")
    private String comment;

    /**
     * Flag indicating whether the rating is not applicable.
     */
    private Boolean nA;

    /**
     * Getter method for ID.
     *
     * @return the unique identifier for the rating
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for ID.
     *
     * @param id the unique identifier for the rating
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for category.
     *
     * @return the category associated with the rating
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setter method for category.
     *
     * @param category the category associated with the rating
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Getter method for question.
     *
     * @return the question being rated
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter method for question.
     *
     * @param question the question being rated
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter method for points.
     *
     * @return the points assigned to the rating
     */
    public int getPoints() {
        return points;
    }

    /**
     * Setter method for points.
     *
     * @param points the points assigned to the rating
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Getter method for comment.
     *
     * @return the additional comment for the rating
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for comment.
     *
     * @param comment the additional comment for the rating
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter method for not-applicable-flag.
     *
     * @return the flag indicating whether the rating is not applicable
     */
    public Boolean getnA() {
        return nA;
    }

    /**
     * Setter method for not-applicable-flag.
     * 
     * @param nA the flag indicating whether the rating is not applicable
     */
    public void setnA(Boolean nA) {
        this.nA = nA;
    }
}
