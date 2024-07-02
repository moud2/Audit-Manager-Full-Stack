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
    private Long id;

    /**
     * Category the rating is in.
     */
    @NotNull(message = "Category can not be null")
    private Category category; 

    /**
     * The question being rated.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Question can not be blank")
    @Size(max = 255, message = "Question should be up to 255 characters")
    private String question;

    /**
     * Evaluation of  Rating.
     * Range from 0 to 5.
     */
    @Min(value = 0, message = "Points can not be less than 0")
    @Max(value = 5, message = "Points can not be more than 5")
    private Integer points;

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
    public Long getId() {
        return id;
    }

    /**
     * Setter method for ID.
     *
     * @param id the unique identifier for the rating
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for category.
     *
     * @return the category of the rating
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setter method for category.
     *
     * @param category the category of the rating
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
     * @return the points given to the rating
     */
    public int getPoints() {
        return points;
    }

    /**
     * Setter method for points.
     *
     * @param points the points given to the rating
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Getter method for comment.
     *
     * @return the comment on the rating
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for comment.
     *
     * @param comment the  comment on the rating
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter method for not-applicable-flag.
     *
     * @return the not-applicable-flag
     */
    public Boolean getnA() {
        return nA;
    }

    /**
     * Setter method for not-applicable-flag.
     * 
     * @param nA the not-applicable-flag
     */
    public void setnA(Boolean nA) {
        this.nA = nA;
    }
}
