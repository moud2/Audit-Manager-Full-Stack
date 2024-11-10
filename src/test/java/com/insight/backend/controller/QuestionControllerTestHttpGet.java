package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.question.DeleteQuestionService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * test class for testing QuestionController
 */
@WebMvcTest(QuestionController.class)
@ExtendWith(SpringExtension.class)
public class QuestionControllerTestHttpGet {
    
    /**
     * MockMvc instance for HTTP request mocking
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindQuestionByCategoryService findQuestionService;
    private DeleteQuestionService deleteQuestionService;

    private Question question1;
    private Question question2;
    private Category category1;

    @BeforeEach
    public void setup() {
        question1 = new Question();
        question2 = new Question();
        category1 = new Category();
        question1.setName("TestQuestion1");
        question2.setName("TestQuestion2");
        category1.setName("TestCategory1");
        category1.setId(3L);
        question1.setId(1L);
        question2.setId(2L);
        question1.setCategory(category1);
        question2.setCategory(category1);
    }

    @Test
    public void testGetQuestionsByCategory() throws Exception {
        // Mock the service to return the Audits
        List<Question> questions = Arrays.asList(question1, question2);
        when(findQuestionService.findQuestionsByCategory(category1, "asc", "id")).thenReturn(questions);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/categories/3L/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestQuestion1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestQuestion2"));
    }

    @Test
    public void testGetEmpties() throws Exception {
        List<Question> questions = new ArrayList<>();
        when(findQuestionService.findQuestionsByCategory(category1,  "asc", "id")).thenReturn(questions);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/categories/3L/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}

