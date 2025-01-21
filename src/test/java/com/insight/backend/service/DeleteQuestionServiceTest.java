package com.insight.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import com.insight.backend.exception.InvalidQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionService;

@ExtendWith(MockitoExtension.class)
public class DeleteQuestionServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private FindQuestionService findQuestionService;

    @InjectMocks
    private DeleteQuestionService deleteQuestionService;

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setName("Test Question");
        question.setDeletedAt(null); // Initially, the question is not deleted
    }

    @Test
    public void testDeleteQuestion_success() {
        Question question = new Question();
        question.setId(1L);
        question.setName("Test Question");
        question.setDeletedAt(null);

        when(questionRepository.saveAndFlush(any(Question.class))).thenAnswer(invocation -> {
            Question updatedQuestion = invocation.getArgument(0);
            updatedQuestion.setDeletedAt(LocalDateTime.now());
            return updatedQuestion;
        });

        deleteQuestionService.deleteQuestion(question);

        assertNotNull(question.getDeletedAt(), "DeletedAt should be set to a non-null value");
        verify(questionRepository, times(1)).saveAndFlush(question);
    }


    @Test
    public void testDeleteQuestion_nullInput() {
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            deleteQuestionService.deleteQuestion(null);
        });
        assertEquals("Question cannot be null", exception.getMessage());
    }
    @Test
    public void testDeleteQuestion_validInput() {
        Question question = new Question();
        question.setId(1L);
        question.setName("Test Question");

        when(questionRepository.saveAndFlush(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        deleteQuestionService.deleteQuestion(question);

        assertNotNull(question.getDeletedAt(), "DeletedAt should be set to a non-null value");
        verify(questionRepository, times(1)).saveAndFlush(question);
    }
/** @Test
    public void testDeleteQuestion_SaveAndFlushCalled() throws Exception {
        when(findQuestionService.findQuestionByID(1L)).thenReturn(Optional.of(question));
        mockMvc.perform(delete("/api/v1/questions/{questionID}", 1L))
            .andExpect(status().isOk())
            .andReturn();
        verify(questionRepository, times(1)).saveAndFlush(question);
    }
*/

}
