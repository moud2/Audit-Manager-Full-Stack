package com.insight.backend.service;

import java.util.Optional;

import com.insight.backend.dto.CategoryResponseDTO;
import com.insight.backend.exception.DuplicateCategoryNameException;
import com.insight.backend.model.Category;
import com.insight.backend.service.category.CreateCategoryService;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.category.SaveCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryServiceTest {

    @Mock
    private FindCategoryService findCategoryService;

    @Mock
    private SaveCategoryService saveCategoryService;

    @InjectMocks
    private CreateCategoryService createCategoryService;

    /**
     * Test case for creating a category successfully.
     */
    @Test
    public void testCreateCategory_Success() {
        String categoryName = "Unique Category";
        Category category = new Category();
        category.setId(1L);
        category.setName(categoryName);

        when(findCategoryService.findCategoryByName(categoryName)).thenReturn(Optional.empty());
        when(saveCategoryService.saveCategory(any(Category.class))).thenReturn(category);  // Gibt das Category-Objekt mit ID zurück

        CategoryResponseDTO response = createCategoryService.createCategory(categoryName);

        assertNotNull(response);
        assertEquals(1L, response.getId());  // Erwartet, dass die ID 1 ist
        assertEquals(categoryName, response.getName());
        verify(saveCategoryService, times(1)).saveCategory(any(Category.class));
    }

    /**
     * Test case for creating a category with a name that already exists.
     */
    @Test
    public void testCreateCategory_DuplicateName_ThrowsException() {
        String categoryName = "Existing Category";
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName(categoryName);

        when(findCategoryService.findCategoryByName(categoryName)).thenReturn(Optional.of(existingCategory));

        assertThrows(DuplicateCategoryNameException.class, () -> createCategoryService.createCategory(categoryName));
        verify(saveCategoryService, never()).saveCategory(any(Category.class));
    }
}

