package com.insight.backend.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.SaveQuestionService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private FindCategoryService findCategoryService;
    @InjectMocks
    private CreateQuestionService createQuestionService;
    @InjectMocks
    private SaveQuestionService saveQuestionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);    
    }

    /*
        Hier nochmal genauer hinschauen und gegebenfalls Test Cases abdecken
     */
    @Test
    public void testCreateQuestion_success() {

        NewQuestionDTO newQuestionDTO = new NewQuestionDTO();
        newQuestionDTO.setName("Question Name");
        newQuestionDTO.setCategory(1L);
        
        Category category1 = new Category();
        category1.setId(1L);
        Question question1 = new Question();
        question1.setId(1L);
        category1.setQuestions(Set.of(question1));

        Category category2 = new Category();
        category2.setId(2L);
        Question question2 = new Question();
        question2.setId(2L);
        category2.setQuestions(Set.of(question2));

        when(findCategoryService.findCategoryById(1L)).thenReturn(Optional.of(category1));
        when(findCategoryService.findCategoryById(2L)).thenReturn(Optional.of(category2));
        when(saveQuestionService.saveQuestion(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            question.setId(1L);
            return question;
        });

        QuestionResponseDTO response = createQuestionService.createQuestion(newQuestionDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Question Name", response.getName());

        verify(saveQuestionService, times(1)).saveQuestion(any(Question.class));
    }



}