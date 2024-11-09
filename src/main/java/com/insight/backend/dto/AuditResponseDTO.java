package com.insight.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data Transfer Object equivalent to JSON object of GET-Request /api/v1/audits and /api/v1/audits/new Response.
 */
public class AuditResponseDTO {

    /**
     * Unique identifier for the Audit.
     */
    private Long id;

    /**
     * The name of the Audit.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name should be up to 255 characters")
    private String name;

    /**
     * The timestamp when the Audit was created.
     * Format the output as ISO 8601 (e.g., "2024-11-07T12:34:56").
     * Must not be null.
     */
    @NotNull(message = "Creation timestamp cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Getter method for ID.
     *
     * @return the unique identifier for the Audit.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for ID.
     *
     * @param id the unique identifier for the Audit.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for Audit name.
     *
     * @return the name of the Audit.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for Audit name.
     *
     * @param name of the audit.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the created timestamp.
     *
     * @return the created timestamp of the Audit.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for the created timestamp.
     *
     * @param createdAt the created timestamp of the Audit.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
