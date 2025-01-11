package com.insight.backend.controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.insight.backend.model.Question;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionService;

@WebMvcTest(controllers = QuestionController.class)
public class QuestionControllerTestHttpDelete {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindQuestionService findQuestionService;

    @MockBean
    private DeleteQuestionService deleteQuestionService;

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

   /** @Test
    public void testDeleteQuestionNotFound() throws Exception {
        // Mock the service to throw QuestionNotFoundException
        when(findQuestionService.findQuestionByID(QUESTION_ID)).thenThrow(new QuestionNotFoundException(QUESTION_ID));

        // Perform delete request and assert response
        mockMvc.perform(delete("/api/v1/questions/{questionID}", QUESTION_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Question with id " + QUESTION_ID + " not found"))
            .andExpect(header().string("Content-Type", "application/json"));

        // Verify interactions
        verify(findQuestionService, times(1)).findQuestionByID(QUESTION_ID);
        verify(deleteQuestionService, times(0)).deleteQuestion(question);
    }

    @Test
    public void testDeleteQuestionWithInvalidID() throws Exception {
        // Perform delete request with an invalid ID
        mockMvc.perform(delete("/api/v1/questions/{questionID}", -1L))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Invalid ID provided"))
            .andExpect(header().string("Content-Type", "application/json"));

        // Verify no interactions with services
        verify(findQuestionService, times(0)).findQuestionByID(-1L);
        verify(deleteQuestionService, times(0)).deleteQuestion(question);
    }*/
}
