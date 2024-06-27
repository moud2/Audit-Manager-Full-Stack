package com.insight.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.Question.SaveQuestionService;

@ExtendWith(MockitoExtension.class)
public class QuestionRepositoryTest {

    @Mock
    private QuestionRepository questionRepository;

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("Feuerwand");
    }

    @Test
    public void saveQuestionTest() {
        SaveQuestionService sv = new SaveQuestionService(questionRepository);
        when(sv.saveQuestion(question)).thenReturn(question);

        Question savedQuestion = questionRepository.save(question);

        assertNotNull(savedQuestion);
        assertEquals("Feuerwand", savedQuestion.getName());
        verify(questionRepository, times(1)).save(question);
    }



   
}
