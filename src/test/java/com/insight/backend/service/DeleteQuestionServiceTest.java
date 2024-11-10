package com.insight.backend.service;

import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.DeleteQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteQuestionServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private DeleteQuestionService deleteQuestionService;

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("Question");
    }

    @Test
    void testDeleteQuestion() {
        when(questionRepository.saveAndFlush(question)).thenReturn(question);
        deleteQuestionService.deleteQuestion(question.getId());
        verify(questionRepository, times(1)).saveAndFlush(question);
        assertNotNull(question.getDeletedAt());
    }
}
