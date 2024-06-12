package com.insight.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
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


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getName() {
        return name;
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
}
