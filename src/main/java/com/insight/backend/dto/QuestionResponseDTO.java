package com.insight.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object equivalent to JSON object of GET-Request /api/v1/audits and /api/v1/audits/new Response.
 */
public class QuestionResponseDTO {

    /**
     * Unique identifier for the Audit.
     */
    private Long id;

    /**
     * The name of the Question.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name should be up to 4096 characters")
    private String name;

    /**
     * Getter method for ID.
     *
     * @return the unique identifier for the Question.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for ID.
     *
     * @param id the unique identifier for the Question.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for Question name.
     *
     * @return the name of the Question.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for Question name.
     *
     * @param name of the Question.
     */
    public void setName(String name) {
        this.name = name;
    }
}
