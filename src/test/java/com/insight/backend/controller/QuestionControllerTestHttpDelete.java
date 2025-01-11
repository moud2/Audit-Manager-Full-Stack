package com.insight.backend.controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.insight.backend.model.Question;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionService;

@WebMvcTest(QuestionController.class)
@ExtendWith(SpringExtension.class)
public class QuestionControllerTestHttpDelete {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindQuestionService findQuestionService;

    @MockBean
    private DeleteQuestionService deleteQuestionService;

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("TestQuestion");
        question.setDeletedAt(null);
    }

    @Test
    public void testDeleteQuestionFound() throws Exception {
        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.of(question));

        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isOk());

        verify(findQuestionService, times(1)).findQuestionByID(1L);
        verify(deleteQuestionService, times(1)).deleteQuestion(question);
    }

    @Test
    public void testDeleteQuestionNotFound() throws Exception {
        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isNotFound());

        verify(findQuestionService, times(1)).findQuestionByID(1L);
        verify(deleteQuestionService, times(0)).deleteQuestion(question);
    }
}
