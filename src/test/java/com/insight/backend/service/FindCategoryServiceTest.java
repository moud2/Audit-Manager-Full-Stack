package com.insight.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.specifications.CategorySpecifications;

/**
 * Test class for FindCategoryService.
 */
@ExtendWith(MockitoExtension.class)
public class FindCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;


    @InjectMocks
    private FindCategoryService findCategoryService;

    private Category category1;
    private Category category2;

    /**
     * Set up method to initialize test data.
     */
    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
        category1.setDeletedAt(null); // Not deleted

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");
        category2.setDeletedAt(null); // Not deleted
    }

    /**
     * Test case for finding a category by ID when the category is found.
     */
    @Test
    void testFindCategoryById_found() {
        // Arrange: Set up mock behavior for categoryRepository
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Optional<Category> foundCategory = findCategoryService.findCategoryById(1L);
        assertEquals(category1, foundCategory.get());
    }

    /**
     * Test case for finding a category by ID when the category is not found.
     */
    @Test
    void testFindCategoryById_notFound() {
        // Arrange: Set up mock behavior for categoryRepository
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Category> foundCategory = findCategoryService.findCategoryById(1L);
        assertTrue(foundCategory.isEmpty());
    }

    @Test
    void testFindAllCategories() {
        // Arrange: Set up mock behavior for categoryRepository
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll(CategorySpecifications.isNotDeleted())).thenReturn(categories); // Ensure only non-deleted categories are returned

        // Act: Call the service method
        List<Category> foundCategories = findCategoryService.findAllCategories();

        // Assert: Verify that only non-deleted categories are returned
        assertEquals(categories, foundCategories);
    }

    /**
     * Test case for finding a category by name when the category is found.
     */
    @Test
    void testFindCategoryByName_found() {
        when(categoryRepository.findByName("Category1")).thenReturn(Optional.of(category1));
        Optional<Category> foundCategory = findCategoryService.findCategoryByName("Category1");

        assertTrue(foundCategory.isPresent());
        assertEquals("Category1", foundCategory.get().getName());
    }

    /**
     * Test case for finding a category by name when the category is not found.
     */
    @Test
    void testFindCategoryByName_notFound() {
        when(categoryRepository.findByName("NonExistentCategory")).thenReturn(Optional.empty());
        Optional<Category> foundCategory = findCategoryService.findCategoryByName("NonExistentCategory");

        assertTrue(foundCategory.isEmpty());
    }
}
