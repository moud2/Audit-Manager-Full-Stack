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

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.Category.SaveCategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Feuerwand");
    }

    @Test
    public void saveCategoryTest() {
        SaveCategoryService sv = new SaveCategoryService(categoryRepository);
        when(sv.saveCategory(category)).thenReturn(category);

        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory);
        assertEquals("Feuerwand", savedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }



   
}
