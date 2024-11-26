package com.insight.backend.controller;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for validating the behavior of {@link AuditsController}.
 * These tests are focused on verifying the correct handling of HTTP POST requests
 * for creating audits, including scenarios with invalid JSON payloads, empty categories,
 * and non-existing categories.
 */
@WebMvcTest(QuestionController.class)
@ExtendWith(SpringExtension.class)
public class QuestionControllerTestHttpPost {

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
    private FindQuestionByCategorieService findQuestionService;

    /**
     * MockBean for CreateAuditService
     */
    @MockBean
    private CreateQuestionService createQuestionService;

    @BeforeEach
    void setUp() {
        // Mocking behavior for createAuditService
        when(createQuestionService.createQuestion(any(NewQuestionDTO.class))).thenReturn(null);
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
        String invalidJson = "{ \"name\": \"Question Name\", \"categories\": ";

        mockMvc.perform(post("/api/v1/question/new")
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
        NewQuestionDTO requestDto = new NewQuestionDTO();
        requestDto.setName("Question Name");
        requestDto.setCategory(null); // Set empty categories list

        mockMvc.perform(post("/api/v1/question/new")
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
    public void testNonExistingCategory() throws Exception {
        NewQuestionDTO newQuestionDTO = new NewQuestionDTO();
        newQuestionDTO.setName("Question Name");
        newQuestionDTO.setCategory(10L);

        when(createQuestionService.createQuestion(any(NewQuestionDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/v1/question/new")
                        .content(objectMapper.writeValueAsString(newQuestionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\": \"non existing category provided\"}"));
    }

    /**
     * Test case for successful creation of an audit.
     * Expects a HTTP 201 Created response with the details of the created audit.
     *
     * @throws Exception if there is an error performing the MVC request
     */
    @Test
    public void testSuccessfulQuestionCreation() throws Exception {
        // Audit DTO with valid categories
        NewQuestionDTO newQuestionDTO = new NewQuestionDTO();
        newQuestionDTO.setName("Question Name");
        newQuestionDTO.setCategory(1L);

        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(1L);
        questionResponseDTO.setName("Question Name");

        // Mocking behavior of createAuditService
        when(createQuestionService.createQuestion(any(NewQuestionDTO.class))).thenReturn(questionResponseDTO);

        mockMvc.perform(post("/api/v1/question/new")
                        .content(objectMapper.writeValueAsString(newQuestionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())  // Expecting status code 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Question Name"));
    }
}