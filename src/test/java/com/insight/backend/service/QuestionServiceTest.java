package com.insight.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.SaveQuestionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    
    @InjectMocks
    private SaveQuestionService saveQuestionService;
    private Question question; 

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("Feuerwand");
    }

    @Test
    public void saveQuestionTest() {
        
        when(questionRepository.saveAndFlush(question)).thenReturn(question);

        Question savedQuestion = saveQuestionService.saveQuestion(question);

        verify(questionRepository, times(1)).saveAndFlush(question);
        
        assertNotNull(savedQuestion);
        assertEquals(question.getName(), savedQuestion.getName());
    }
}
