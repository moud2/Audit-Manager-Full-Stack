package com.insight.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.exception.QuestionFoundException;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionServiceTest {

    @Mock
    private FindCategoryService findCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @Mock
    private FindQuestionByCategoryService findQuestionService;

    @InjectMocks
    private CreateQuestionService createQuestionService;

    /**
     * Tests the successful creation of a new question.
     * - Verifies that the category exists.
     * - Ensures no duplicate questions exist.
     * - Checks that the question is saved and the correct response is returned.
     */
    @Test
    public void testCreateQuestion_success() {
        // Arrange
        NewQuestionDTO newQuestionDTO = new NewQuestionDTO();
        newQuestionDTO.setName("Question Name");
        newQuestionDTO.setCategoryId(1L);

        Category category1 = new Category();
        category1.setId(1L);
        Question question1 = new Question();
        question1.setName("Question Name1");
        question1.setId(1L);
        category1.setQuestions(Set.of(question1));

        when(findCategoryService.findCategoryById(1L)).thenReturn(Optional.of(category1));
        when(findQuestionService.findQuestionsByName("Question Name", "desc", "name")).thenReturn(List.of());
        when(saveQuestionService.saveQuestion(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            question.setId(1L);
            return question;
        });
        
        // Act (calling createQuestion)
        QuestionResponseDTO response = createQuestionService.createQuestion(newQuestionDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Question Name", response.getName());
        assertNotNull(response.getId());
        assertEquals(1L, response.getId());

        // verify function of Mocks
        verify(findCategoryService, times(1)).findCategoryById(1L);
        verify(findQuestionService, times(1)).findQuestionsByName("Question Name", "desc", "name");
        verify(saveQuestionService, times(1)).saveQuestion(any(Question.class));
    }

    /**
     * Tests creating a question with a duplicate name.
     */
    @Test
    public void testCreateQuestion_duplicateQuestion() {
        // Arrange
        NewQuestionDTO duplicateQuestionDTO = new NewQuestionDTO();
        duplicateQuestionDTO.setName("Duplicate Question");
        duplicateQuestionDTO.setCategoryId(1L);

        when(findQuestionService.findQuestionsByName("Duplicate Question", "desc", "name"))
                .thenReturn(List.of(new Question()));

        // Act & Assert
        //tests how the createQuestion method behaves when attempting to create a duplicate question
        assertThrows(QuestionFoundException.class, () -> {
            createQuestionService.createQuestion(duplicateQuestionDTO);
        });

        verify(findQuestionService, times(1)).findQuestionsByName("Duplicate Question", "desc", "name");
        verify(findCategoryService, times(0)).findCategoryById(anyLong());
        verify(saveQuestionService, times(0)).saveQuestion(any(Question.class));
    }
    /**
     * Tests creating a question with an invalid category ID.
     */
    
}