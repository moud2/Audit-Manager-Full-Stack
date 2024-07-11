package com.insight.backend.controller;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.dto.NewAuditDTO;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Unit tests for validating the behavior of {@link AuditsController}.
 * These tests are focused on verifying the correct handling of HTTP POST requests
 * for creating audits, including scenarios with invalid JSON payloads and empty categories.
 */
@WebMvcTest(AuditsController.class)
public class AuditsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test case for validating handling of invalid JSON payload.
     * Expects a HTTP 400 Bad Request response.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testInvalidJson() throws Exception {
        // Malformed JSON payload
        String invalidJson = "{ \"name\": \"Audit Name\", \"categories\": ";

        mockMvc.perform(post("/api/v1/audits/new")
                .content(invalidJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case for validating handling of empty categories in the audit DTO.
     * Expects a HTTP 400 Bad Request response with a specific error message.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testEmptyCategories() throws Exception {
        // Audit DTO with empty categories list
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Audit Name");
        newAuditDTO.setCategories(Collections.emptyList());

        mockMvc.perform(post("/api/v1/audits/new")
                .content(objectMapper.writeValueAsString(newAuditDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.categories").value("Categories cannot be empty"));
    }
}
