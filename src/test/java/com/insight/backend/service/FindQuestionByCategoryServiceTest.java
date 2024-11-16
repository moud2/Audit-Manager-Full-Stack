package com.insight.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Question;
import com.insight.backend.model.Category;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindQuestionByCategoryServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private FindQuestionByCategoryService findQuestionService;

    private Question question1;
    private Question question2;
    private Category category;

    @BeforeEach
    void setUp() {
        question1 = new Question();
        question1.setId(1L);
        question1.setName("Question1");
        question1.setCategory(category);

        question2 = new Question();
        question2.setId(2L);
        question2.setName("Question2");
        question2.setCategory(category);
    }

    @Test
    void testFindQuestionById_found() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));

        Optional<Question> foundQuestion = findQuestionService.findQuestionByID(1L);

        assertEquals(question1, foundQuestion.get());
    }

    @Test
    void testFindQuestionById_notFound() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Question> foundQuestion = findQuestionService.findQuestionByID(1L);

        assertTrue(foundQuestion.isEmpty());
    }

    @Test
    void testFindQuestionsByCategory() {
        List<Question> questions = Arrays.asList(question1, question2);
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> foundQuestions = findQuestionService.findQuestionsByCategory(category, "asc", "id");

        assertEquals(questions, foundQuestions);
    }
}
