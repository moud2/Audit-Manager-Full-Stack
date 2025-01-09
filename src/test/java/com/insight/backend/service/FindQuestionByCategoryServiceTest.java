package com.insight.backend.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
        question1.setName("question1");
        question2 = new Question();
        question2.setId(2L);
        question2.setName("question2");
        
        category = new Category();
        category.setId(3L);
        category.setName("Category");
        category.setQuestions(new HashSet<Question>());
        category.getQuestions().add(question1);
        category.getQuestions().add(question2);

        question1.setCategory(category);
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
        question2.setDeletedAt(LocalDateTime.now());
        List<Question> questions = Arrays.asList(question1);
        when(questionRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(questions);
        
        List<Question> foundQuestions = findQuestionService.findQuestionsByCategory(category, "asc", "id");

        Category test = question1.getCategory();
        assertEquals(category, test);

        assertEquals(1, foundQuestions.size());
        assertEquals("question1", foundQuestions.getFirst().getName());
    }
}
