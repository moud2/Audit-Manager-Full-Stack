package com.insight.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


import com.insight.backend.model.Audit;
import com.insight.backend.service.Audit.FindAuditService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
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
