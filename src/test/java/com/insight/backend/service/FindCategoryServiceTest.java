package com.insight.backend.service;

import java.time.LocalDateTime;
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
    private Category category3; // Deleted category

    /**
     * Set up method to initialize test data.
     * This method is executed before each test to ensure a clean state.
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

        category3 = new Category();
        category3.setId(3L);
        category3.setName("Category3");
        category3.setDeletedAt(LocalDateTime.now()); // Deleted category
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

    /**
     * Test case for finding all non-deleted categories.
     */
    @Test
    void testFindAllCategories() {
        // Arrange: Mock behavior to return only non-deleted categories
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll(CategorySpecifications.isNotDeleted())).thenReturn(categories);

        // Act: Call the service method
        List<Category> foundCategories = findCategoryService.findAllCategories();

        // Assert: Verify that only non-deleted categories are returned
        assertEquals(categories, foundCategories);
    }

    /**
     * Test case for finding categories including deleted ones.
     */
    @Test
    void testFindAllCategoriesIncludingDeleted() {
        // Arrange: Mock behavior to return all categories, including deleted ones
        List<Category> categories = Arrays.asList(category1, category2, category3);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act: Call the service method
        List<Category> foundCategories = findCategoryService.findAllCategoriesIncludingDeleted();

        // Assert: Verify that all categories, including deleted ones, are returned
        assertEquals(categories, foundCategories);
    }
}
