package com.insight.backend.model;

/**
 * Represents a rating in the system.
 */
public class Rating {
    private int id;
    private Category category;
    private String question;
    private String comment;
    private int points;
    private Boolean nA;

    /**
     * Constructs a new Rating with the specified parameters.
     *
     * @param id        the ID of the rating
     * @param category  the category associated with the rating
     * @param question  the question for the rating
     * @param points    the points awarded for the rating
     * @param comment   the comment associated with the rating
     * @param nA        indicates if the rating is not applicable
     */
    public Rating(int id, Category category, String question, int points, String comment, Boolean nA) {
        setRatingId(id);
        setCategory(category);
        setQuestion(question);
        setComment(comment);
        setPoints(points);
        setNA(nA);
    }

    /**
     * Sets the ID of the rating.
     *
     * @param id the ID to set
     */
    public void setRatingId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the rating.
     *
     * @return the ID of the rating
     */
    public int getRatingId() {
        return this.id;
    }

    /**
     * Sets the category of the rating.
     *
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the category of the rating.
     *
     * @return the category of the rating
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Sets the question of the rating.
     *
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the question of the rating.
     *
     * @return the question of the rating
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Sets the comment of the rating.
     *
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the comment of the rating.
     *
     * @return the comment of the rating
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the points of the rating.
     *
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the points of the rating.
     *
     * @return the points of the rating
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Sets the not applicable status of the rating.
     *
     * @param nA the not applicable status to set
     */
    public void setNA(Boolean nA) {
        this.nA = nA;
    }

    /**
     * Returns the not applicable status of the rating.
     *
     * @return the not applicable status of the rating
     */
    public Boolean getNA() {
        return this.nA;
    }
}