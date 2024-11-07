package com.insight.backend.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;

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
     * The name of the Customer.
     * Must not be empty and should not exceed 255 characters.
     */
    @NotEmpty(message = "Customer cannot be empty")
    @Size(max = 255, message = "Customer should be up to 255 characters")
    private String customer;

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
     * Getter method for Customer.
     *
     * @return the customer of the Audit.
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Setter method for Customer.
     *
     * @param customer of the audit.
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
