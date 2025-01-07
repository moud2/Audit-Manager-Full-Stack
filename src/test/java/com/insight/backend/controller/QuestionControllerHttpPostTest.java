package com.insight.backend.controller;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for validating the behavior of {@link QuestionController}.
 * These tests are focused on verifying the correct handling of HTTP POST requests
 * for creating audits, including scenarios with invalid JSON payloads, empty categories,
 * and non-existing categories.
 */
@WebMvcTest(QuestionController.class)
@ExtendWith(SpringExtension.class)
public class QuestionControllerHttpPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeleteQuestionService deleteQuestionService;
    @MockBean
    private FindQuestionByCategoryService findQuestionService;



    @MockBean
    private FindCategoryService findCategoryService;

    /**
     * MockBean for CreateAuditService
     */

    @MockBean
    private CreateQuestionService createQuestionService;

    @BeforeEach
    void setUp() {
        when(createQuestionService.createQuestion(any(NewQuestionDTO.class)))
                .thenReturn(null);
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

        mockMvc.perform(post("/api/v1/questions/new")
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
        String invalidJson = "{ \"name\": \"Question Name\" }";


        mockMvc.perform(post("/api/v1/questions/new")
                        .content(invalidJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
        newQuestionDTO.setCategoryId((long) 1);

        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId((long) 1);
        questionResponseDTO.setName("Question Name");

        // Mocking behavior of createAuditService
        when(createQuestionService.createQuestion(any(NewQuestionDTO.class))).thenReturn(questionResponseDTO);

        mockMvc.perform(post("/api/v1/questions/new")
                        .content(objectMapper.writeValueAsString(newQuestionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())  // Expecting status code 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Question Name"));
    }
}