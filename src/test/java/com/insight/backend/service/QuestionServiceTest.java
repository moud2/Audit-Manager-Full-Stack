package com.insight.backend.service;

import com.insight.backend.model.Question;
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
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    
    @InjectMocks
    private SaveQuestionService saveQuestionService;
    private Question question;

    /*
     * Test data to create before execution of and be used in single test method
     */
    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("Feuerwand");
    }

    /*
     * Test method for saving question, compares audit to be saved with actual saved audit
     */
    @Test
    public void saveQuestionTest() {

        when(questionRepository.saveAndFlush(question)).thenReturn(question);

        Question savedQuestion = saveQuestionService.saveQuestion(question);

        verify(questionRepository, times(1)).saveAndFlush(question);
        
        assertNotNull(savedQuestion);
        assertEquals(question, savedQuestion);
    }

    /*
     * Test method for saving with null object, tests for actual saved question being null as well
     */
    @Test
    public void saveNullQuestionTest() {
        assertNull(saveQuestionService.saveQuestion(null));
    }

    @Test
    public void testConstructor() {

        SaveQuestionService saveQuestionServiceCons = new SaveQuestionService(questionRepository);

        assertNotNull(saveQuestionServiceCons);
    }
}
