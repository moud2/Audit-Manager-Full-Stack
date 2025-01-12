package com.insight.backend.service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.rating.CsvGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test class for the {@link CsvGeneratorService}.
 *
 * This test suite verifies the behavior of the CsvGeneratorService class,
 * ensuring that categories and questions with 'deletedAt' values are excluded from the CSV export.
 */
class CsvGeneratorServiceTest {

    /**
     * Mocked repository for retrieving category data.
     */
    @Mock
    private CategoryRepository categoryRepository;

    /**
     * Service under test, with dependencies injected.
     */
    @InjectMocks
    private CsvGeneratorService csvGeneratorService;

    /**
     * Mock data representing categories and questions.
     */
    private List<Category> categories;

    /**
     * Sets up the mock environment and initializes test data before each test method.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock categories and questions
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");
        category2.setDeletedAt(LocalDateTime.now()); // Marked as deleted

        Question question1 = new Question();
        question1.setId(1L);
        question1.setName("Question 1");
        question1.setCategory(category1);

        Question question2 = new Question();
        question2.setId(2L);
        question2.setName("Question 2");
        question2.setCategory(category1);
        question2.setDeletedAt(LocalDateTime.now()); // Marked as deleted

        Question question3 = new Question();
        question3.setId(3L);
        question3.setName("Question 3");
        question3.setCategory(category2);

        category1.setQuestions(new HashSet<>(List.of(question1, question2)));
        category2.setQuestions(new HashSet<>(List.of(question3)));

        categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
    }

    /**
     * Tests that categories with 'deletedAt' values are excluded from the CSV export.
     */
    @Test
    void testDeletedCategoriesAreExcluded() {
        when(categoryRepository.findAll()).thenReturn(categories);

        ByteArrayInputStream csvStream = csvGeneratorService.createCsv();
        String csvContent = new String(csvStream.readAllBytes());

        // Verify that Category 2 (deleted) is not in the CSV
        assertFalse(csvContent.contains("Category 2"));
    }

    /**
     * Tests that questions with 'deletedAt' values are excluded from the CSV export.
     */
    @Test
    void testDeletedQuestionsAreExcluded() {
        when(categoryRepository.findAll()).thenReturn(categories);

        ByteArrayInputStream csvStream = csvGeneratorService.createCsv();
        String csvContent = new String(csvStream.readAllBytes());

        // Verify that Question 2 (deleted) is not in the CSV
        assertFalse(csvContent.contains("Question 2"));

        // Verify that Question 1 (not deleted) is in the CSV
        assertTrue(csvContent.contains("Question 1"));
    }
}
