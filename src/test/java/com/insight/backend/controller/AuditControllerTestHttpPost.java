package com.insight.backend.controller;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.exception.NonExistentAuditCategoryException;
import com.insight.backend.service.audit.AuditProgressService;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.DeleteAuditService;
import com.insight.backend.service.audit.FindAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for validating the behavior of {@link AuditsController}.
 * These tests are focused on verifying the correct handling of HTTP POST requests
 * for creating audits, including scenarios with invalid JSON payloads, empty categories,
 * and non-existing categories.
 */
@WebMvcTest(AuditsController.class)
@ExtendWith(SpringExtension.class)
public class AuditControllerTestHttpPost {

    /**
     * MockMvc instance for HTTP request mocking
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * MockBean for FindAutitService
     */
    @MockBean
    private FindAuditService findAuditService;

    @MockBean
    private AuditProgressService auditProgressService;


    /**
     * MockBean for DeleteAuditService
     */
    @MockBean
    private DeleteAuditService deleteAuditService;

    /**
     * MockBean for CreateAuditService
     */
    @MockBean
    private CreateAuditService createAuditService;

    @BeforeEach
    void setUp() {
        // Mocking behavior for createAuditService
        when(createAuditService.createAudit(any(NewAuditDTO.class))).thenReturn(null);
    }

    /**
     * Test case for validating handling of invalid JSON payload.
     * Expects a HTTP 400 Bad Request response.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testCorruptJson() throws Exception {
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
    public void testInvalidJson() throws Exception {
        NewAuditDTO requestDto = new NewAuditDTO();
        requestDto.setName("Audit Name");
        requestDto.setCategories(Collections.emptyList()); // Set empty categories list

        mockMvc.perform(post("/api/v1/audits/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case for validating handling of non-existing categories.
     * Expects a HTTP 400 Bad Request response with a specific error message.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testNonExistingCategories() throws Exception {
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Audit Name");
        newAuditDTO.setCustomer("TestCustomer");
        newAuditDTO.setCategories(Arrays.asList(1L, 2L));

    when(createAuditService.createAudit(any(NewAuditDTO.class))).thenThrow(new NonExistentAuditCategoryException(1L));

    mockMvc.perform(post("/api/v1/audits/new")
                    .content(objectMapper.writeValueAsString(newAuditDTO))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()); // Expecting status code 400
}


    /**
     * Test case for successful creation of an audit.
     * Expects a HTTP 201 Created response with the details of the created audit.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testSuccessfulAuditCreation() throws Exception {
        // Audit DTO with valid categories
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Audit Name");
        newAuditDTO.setCategories(Collections.singletonList(1L));
        newAuditDTO.setCustomer("TestCustomer");

        AuditResponseDTO auditResponseDTO = new AuditResponseDTO();
        auditResponseDTO.setId(1L);
        auditResponseDTO.setName("Audit Name");
        auditResponseDTO.setCreatedAt(java.time.LocalDateTime.now());
        auditResponseDTO.setCustomer("TestCustomer");

        // Mocking behavior of createAuditService
        when(createAuditService.createAudit(any(NewAuditDTO.class))).thenReturn(auditResponseDTO);

        mockMvc.perform(post("/api/v1/audits/new")
                        .content(objectMapper.writeValueAsString(newAuditDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())  // Expecting status code 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Audit Name"))
                .andExpect(jsonPath("$.customer").value("TestCustomer"))
                .andExpect(jsonPath("$.name").value("Audit Name"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    /**
     * Test case for validating handling of duplicate Category-IDs.
     * Expects a HTTP 400 Bad Request response with a specific error message.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testDuplicateCategoryIds() throws Exception {
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Audit Name");
        newAuditDTO.setCustomer("Audit Customer");
        newAuditDTO.setCategories(Arrays.asList(1L, 1L)); // Duplicate Category-IDs

        // Mock the service to throw IllegalArgumentException
        when(createAuditService.createAudit(any(NewAuditDTO.class)))
                .thenThrow(new IllegalArgumentException("Duplicate Category-IDs are not allowed."));

        mockMvc.perform(post("/api/v1/audits/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAuditDTO)))
                .andExpect(status().isBadRequest()) // Expecting status code 400
                .andExpect(jsonPath("$").value("Duplicate Category-IDs are not allowed.")); // Error message validation
    }

}