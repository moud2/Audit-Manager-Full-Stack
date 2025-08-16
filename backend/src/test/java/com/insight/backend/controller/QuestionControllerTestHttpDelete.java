package com.insight.backend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.insight.backend.exception.QuestionNotFoundException;
import com.insight.backend.service.category.FindCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import com.insight.backend.model.Question;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionService;
import com.insight.backend.service.question.CreateQuestionService;

@WebMvcTest(controllers = QuestionController.class)
public class QuestionControllerTestHttpDelete {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindQuestionService findQuestionService;

    @MockBean
    private DeleteQuestionService deleteQuestionService;

    @MockBean // Add this mock
    private CreateQuestionService createQuestionService;

    @MockBean
    private FindCategoryService findCategoryService; // Added this mock


    private Question question;

    @BeforeEach
    public void setup() {
        question = new Question();
        question.setId(1L);
        question.setName("TestQuestion");
        question.setDeletedAt(null);
    }

    @Test
    public void testDeleteQuestionFound() throws Exception {
        // Mock the service to return the question
        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.of(question));

        // Perform delete request and assert response
        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isOk());


        // Verify interactions
        verify(findQuestionService, times(1)).findQuestionByID(1L);
        verify(deleteQuestionService, times(1)).deleteQuestion(question);
    }

  @Test
    public void testDeleteQuestionNotFound() throws Exception {
        // Mock the service to throw QuestionNotFoundException
        when(findQuestionService.findQuestionByID(1L)).thenThrow(new QuestionNotFoundException(1L));

        // Perform delete request and assert response
        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Question with id '" + 1L + "' not found"))
            .andExpect(header().string("Content-Type", "application/json"));

        // Verify interactions
        verify(findQuestionService, times(1)).findQuestionByID(1L);
        verify(deleteQuestionService, times(0)).deleteQuestion(question);
    }


}
