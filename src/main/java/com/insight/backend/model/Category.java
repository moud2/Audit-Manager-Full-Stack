package com.insight.backend.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Represents a category in the system.
 * The Category class holds information about the category, including its name,
 * associated questions, and a timestamp for soft deletion.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Unique identifier for the category

    @Column(nullable = false)
    private String name; 

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Question> questions; 

    private LocalDateTime deletedAt; // Timestamp for soft deletion; null if not deleted

    /**
     * Gets the timestamp indicating when the category was deleted.
     *
     * @return LocalDateTime representing the deletion timestamp, or null if not deleted.
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * Sets the deletion timestamp for the category.
     *
     * @param deletedAt LocalDateTime representing the deletion time.
     */
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * Constructor for creating a new Category with specified name and questions.
     *
     * @param name the name of the category.
     * @param questions the set of questions associated with the category.
     */
    public Category(String name, Set<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.deletedAt = null; // Initially, the category is not deleted
    }

    /**
     * Default constructor for creating an empty Category.
     * Initializes deletedAt to null.
     */
    public Category() {
        this.deletedAt = null; // Set deletedAt to null for new categories
    }

    /**
     * Gets the set of questions associated with this category.
     *
     * @return Set of questions linked to the category.
     */
    public Set<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the questions associated with this category.
     *
     * @param questions Set of questions to be linked to the category.
     */
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    /**
     * Gets the unique identifier of the category.
     *
     * @return Long representing the category ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the category.
     *
     * @param id Long representing the category ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the category.
     *
     * @return String representing the category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name String representing the new name for the category.
     */
    public void setName(String name) {
        this.name = name;
    }
}
