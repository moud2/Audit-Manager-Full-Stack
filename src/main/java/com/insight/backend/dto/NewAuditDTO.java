package com.insight.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object equivalent to JSON object of GET /api/v1/audits/new.
 */
public class NewAuditDTO {

    /**
     * The supplied name of the audit during creation.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name should be up to 255 characters")
    private String name;

    /**
     * The list of category IDs associated with the audit.
     * Must not be null or empty.
     */
    @NotEmpty(message = "Categories cannot be empty")
    private List<@NotNull(message = "Category ID cannot be null") Long> categories;

    /**
     * Gets the name of the audit.
     *  
     * @return the name of the audit
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the audit.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of category IDs associated with the audit.
     *
     * @return the list of category IDs
     */
    public List<Long> getCategories() {
        return categories;
    }

    /**
     * Sets the list of category IDs associated with the audit.
     *
     * @param categories the list of category IDs to set
     */
    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}

 