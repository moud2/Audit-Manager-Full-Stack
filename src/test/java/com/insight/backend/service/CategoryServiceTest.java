package com.insight.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.category.SaveCategoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SaveCategoryService saveCategoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Feuerwand");
    }

    @Test
    public void saveCategoryTest() {
        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        Category savedCategory = saveCategoryService.saveCategory(category);
       
        verify(categoryRepository, times(1)).saveAndFlush(category);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
    }
}
