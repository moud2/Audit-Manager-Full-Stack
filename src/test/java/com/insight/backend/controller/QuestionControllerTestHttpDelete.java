package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.category.FindCategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
@ExtendWith(SpringExtension.class)
public class QuestionControllerTestHttpDelete {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindQuestionByCategoryService findQuestionService;

    @MockBean
    private DeleteQuestionService deleteQuestionService;

    @MockBean
    private FindCategoryService findCategoryService;

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("TestQuestion");
        question.setDeletedAt(null);
    }

    @Test
    public void testDeleteAuditFound() throws Exception {

        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.of(question));

        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isOk())
            .andReturn();

        verify(deleteQuestionService, times(1)).deleteQuestion(question);

    }

    @Test
    public void testDeleteAuditNotFound() throws Exception {

        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isNotFound())
            .andReturn();

        verify(deleteQuestionService, times(0)).deleteQuestion(question);

    }
}
