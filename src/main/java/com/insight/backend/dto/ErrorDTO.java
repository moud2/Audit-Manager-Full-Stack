package com.insight.backend.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object to represent error responses
 */
public class ErrorDTO {

    /**
     * Error message.
     * Must not be blank.
     */
    @NotBlank(message = "Error message cannot be blank")
    @Size(max = 255, message = "Name should be up to 255 characters")
    private String error;

    /**
     * No-arg constructor (required for frameworks)
     */
    public ErrorDTO() {}

    /**
     * Constructor to initialize error message
     * @param error error message
     */
    public ErrorDTO(String error) {
        this.error = error;
    }

    /**
     * Getter for error message
     * @return error message
     */
    public String getError() {
        return error;
    }

    /**
     * Setter for error message
     * @param error error message
     */
    public void setError(String error) {
        this.error = error;
    }
}
