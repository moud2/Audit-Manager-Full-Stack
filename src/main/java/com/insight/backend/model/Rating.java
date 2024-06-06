package com.insight.backend.model;

public class Rating {
    private int id;
    private Category category;
    private String question;
    private String comment; 
    private int points;
    private Boolean nA;

    public Rating(int id, Category category, String question, int points, String comment, Boolean nA) {
        setRatingId(id);
        setCategory(category);
        setQuestion(question);
        setComment(comment);
        setPoints(points);
        setNA(nA);
    }

    public void setRatingId(int id) {
        this.id = id;
    }

    public int getRatingId() {
        return this.id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public void setNA(Boolean nA) {
        this.nA = nA;
    }

    public Boolean getNA() {
        return this.nA;
    }
}