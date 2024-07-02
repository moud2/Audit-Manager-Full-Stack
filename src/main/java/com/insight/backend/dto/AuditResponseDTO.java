package com.insight.backend.dto;

import jakarta.validation.constraints.*;

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
     * Getter method for ID.
     *
     * @return the unique identifier for the Audit.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for ID.
     *
     * @param id the unique identifier for the Audit.
     */
    public void setId(long id) {
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
}
