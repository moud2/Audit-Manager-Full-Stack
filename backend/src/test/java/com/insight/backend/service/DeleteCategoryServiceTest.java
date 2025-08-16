package com.insight.backend.service;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.insight.backend.exception.CategoryAlreadyDeletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.category.DeleteCategoryService;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DeleteCategoryService deleteCategoryService;

    private Category category;    
    private Category deletedCategory;
    
    /**
     * Initialisiert Testdaten vor jedem Test.
     */
    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDeletedAt(null);
        category.setQuestions(Collections.emptySet());
    
        deletedCategory = new Category();
        deletedCategory.setId(2L);
        deletedCategory.setName("Deleted Category");
        deletedCategory.setDeletedAt(LocalDateTime.now());
    }

    /**
     * Testet die erfolgreiche Soft-Löschung einer aktiven Kategorie.
     */
    @Test
    public void softDeleteCategoryTest() {
        deleteCategoryService.softDeleteCategory(category);
        assertNotNull(category.getDeletedAt(), "Category should have a deletion timestamp.");
        verify(categoryRepository, times(1)).save(category);
    }

    /**
     * Testet, dass eine bereits gelöschte Kategorie nicht erneut gelöscht wird.
     */
    @Test
    public void softDeleteAlreadyDeletedCategoryTest() {
        CategoryAlreadyDeletedException exception = assertThrows(CategoryAlreadyDeletedException.class, () -> {
            deleteCategoryService.softDeleteCategory(deletedCategory);
        });

        assertEquals("Category is already deleted or does not exist.", exception.getMessage());
        verify(categoryRepository, times(0)).save(deletedCategory);
    }

    /**
     * Testet die Soft-Löschung einer Kategorie ohne Fragen.
     */
    @Test
    public void softDeleteCategoryWithoutQuestionsTest() {
        category.setQuestions(Collections.emptySet());
        deleteCategoryService.softDeleteCategory(category);

        assertNotNull(category.getDeletedAt(), "Category should have a deletion timestamp.");
        verify(categoryRepository, times(1)).save(category);
    }
}
