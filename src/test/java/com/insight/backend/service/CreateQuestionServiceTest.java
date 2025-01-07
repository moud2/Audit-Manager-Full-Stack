package com.insight.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionServiceTest {

    @Mock
    private FindCategoryService findCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @Mock
    private FindQuestionByCategoryService findQuestionService;

    @Mock
    private SaveCategoryService saveCategoryService;

    @InjectMocks
    private CreateQuestionService createQuestionService;


    /*
        Hier nochmal genauer hinschauen und gegebenfalls Test Cases abdecken
     */
    @Test
    public void testCreateQuestion_success() {
        Category category1 = new Category();
        category1.setId((long) 1);
        Question question1 = new Question();
        question1.setName("Question Name1");
        question1.setId((long) 1);
        category1.setQuestions(Set.of(question1));

        Category category2 = new Category();
        category2.setId((long) 2);
        Question question2 = new Question();
        question2.setName("Question Name2");
        question2.setId((long) 2);
        category2.setQuestions(Set.of(question2));


        NewQuestionDTO newQuestionDTO = new NewQuestionDTO();
        newQuestionDTO.setName("Question Name");
        newQuestionDTO.setCategory((long) 1);

        QuestionResponseDTO response = createQuestionService.createQuestion(newQuestionDTO);

        assertNotNull(response);
        assertEquals("Question Name", response.getName());

        verify(saveQuestionService, times(1)).saveQuestion(any(Question.class));
    }



}