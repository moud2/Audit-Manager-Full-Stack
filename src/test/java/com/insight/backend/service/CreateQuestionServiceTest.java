package com.insight.backend.service;

import com.insight.backend.model.Question;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private CreateQuestionService createQuestionService;

    private NewQuestionDTO question;

    @BeforeEach
    void setUp() {
        question = new NewQuestionDTO();
        question.setId("9999");
        question.setName("Question");
    }

    @Test
    void testCreateQuestion_success() {
        when(questionRepository.saveAndFlush(question)).thenReturn(question);
        createQuestionService.createQuestion(question)
        verify(questionRepository, times(1)).saveAndFlush(question);
        assertNotNull(question.getDeletedAt());
    }
}