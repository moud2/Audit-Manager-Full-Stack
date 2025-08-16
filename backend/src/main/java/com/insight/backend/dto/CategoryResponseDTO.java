package com.insight.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryResponseDTO {

    private final Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name should be up to 255 characters")
    private final String name;

    /**
     * Constructor to create a new CategoryResponseDTO object.
     *
     * @param id the unique identifier for the category
     * @param name the name of the category
     */
    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter method for ID.
     *
     * @return the unique identifier for the category
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter method for name.
     *
     * @return the name of the category
     */
    public String getName() {
        return name;
    }
}
