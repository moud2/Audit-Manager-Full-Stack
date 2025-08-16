package com.insight.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.exception.CategoryNotFoundException;
import com.insight.backend.exception.QuestionAlreadyExistsException;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.FindQuestionService;
import com.insight.backend.service.question.SaveQuestionService;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionServiceTest {

    @Mock
    private FindCategoryService findCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @Mock
    private FindQuestionService findQuestionService;

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

        when(findCategoryService.findCategoryById(1L)).thenReturn(Optional.of(category1));
        when(findQuestionService.findQuestionsByName("Question Name", "desc", "name")).thenReturn(List.of());
        when(saveQuestionService.saveQuestion(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            question.setId(1L);
            return question;
        });
        
        // Act
        QuestionResponseDTO response = createQuestionService.createQuestion(newQuestionDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Question Name", response.getName());
        assertNotNull(response.getId());
        assertEquals(1L, response.getId());
        assertEquals(category1, response.getCategory());

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
        assertThrows(QuestionAlreadyExistsException.class, () -> {
            createQuestionService.createQuestion(duplicateQuestionDTO);
        });

        verify(findQuestionService, times(1)).findQuestionsByName("Duplicate Question", "desc", "name");
        verify(findCategoryService, times(0)).findCategoryById(anyLong());
        verify(saveQuestionService, times(0)).saveQuestion(any(Question.class));
    }
    /**
     * Tests creating a question with an invalid category ID.
     */
    @Test
    public void testCreateQuestion_invalidCategory(){
        // Arrange
        NewQuestionDTO invalidCategoryDTO = new NewQuestionDTO();
        invalidCategoryDTO.setName("Invalid Category Question");
        invalidCategoryDTO.setCategoryId(99L);

        when(findCategoryService.findCategoryById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            createQuestionService.createQuestion(invalidCategoryDTO);
        });

        // verify function of Mocks
        verify(findCategoryService, times(1)).findCategoryById(99L);
    }
}