package com.insight.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isNa = false;
    private String comment = "";
    @Min(0)
    @Max(5)
    private Integer points;
    @ManyToOne
    @JoinColumn(name="audit_id", nullable=false)
    private Audit audit;
    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;


    public Rating(Boolean isNa, String comment, Integer points, Audit audit, Question question) {
        this.isNa = isNa;
        this.comment = comment;
        this.points = points;
        this.audit = audit;
        this.question = question;
    }

    public Rating() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNa(Boolean na) {
        isNa = na;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPoints(@Min(0) @Max(5) Integer points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public Boolean getNa() {
        return isNa;
    }

    public String getComment() {
        return comment;
    }

    public @Min(0) @Max(5) Integer getPoints() {
        return points;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
