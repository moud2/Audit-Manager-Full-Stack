package com.insight.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.insight.backend.dto.AuditProgressDTO;
import com.insight.backend.dto.CategoryProgressDTO;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

import com.insight.backend.service.audit.AuditProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for the {@link AuditProgressService}.
 *
 * This test suite verifies the behavior of the AuditProgressService class,
 * which is responsible for calculating the progress of an audit.
 * The progress includes overall progress, per-category progress,
 * and question counts by ratings.
 */
class AuditProgressServiceTest {

    /**
     * Mocked repository for retrieving rating data associated with an audit.
     */
    @Mock
    private RatingRepository ratingRepository;

    /**
     * Service under test, with dependencies injected.
     */
    @InjectMocks
    private AuditProgressService auditProgressService;

    /**
     * Mock data representing a list of ratings associated with an audit.
     */
    private List<Rating> ratings;

    /**
     * Sets up the mock environment and initializes test data before each test method.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock data
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        Question question1 = new Question();
        question1.setId(1L);
        question1.setCategory(category1);

        Question question2 = new Question();
        question2.setId(2L);
        question2.setCategory(category2);

        Rating rating1 = new Rating();
        rating1.setPoints(4);
        rating1.setNa(false);
        rating1.setQuestion(question1);

        Rating rating2 = new Rating();
        rating2.setPoints(5);
        rating2.setNa(false);
        rating2.setQuestion(question2);

        // Sicherstellen, dass beide Kategorien getestet werden
        ratings = new ArrayList<>();
        ratings.add(rating1);
        ratings.add(rating2);
    }

    /**
     * Tests the calculation of audit progress with valid ratings data.
     *
     * Verifies that the progress is calculated correctly, including overall progress,
     * per-category progress, and question counts by ratings.
     */
    @Test
    void testCalculateAuditProgress() {
        when(ratingRepository.findByAuditId(1L)).thenReturn(ratings);

        AuditProgressDTO result = auditProgressService.calculateAuditProgress(1L);

        assertEquals(2, result.getCategoryProgress().size());
        assertEquals(90.0, result.getCurrentAuditProgress(), 0.01);

        CategoryProgressDTO category1Progress = result.getCategoryProgress().get(0);
        assertEquals("Category 1", category1Progress.getCategoryName());
        assertEquals(1, category1Progress.getAnsweredQuestions());
        assertEquals(1, category1Progress.getTotalQuestions());
        assertEquals(80.0, category1Progress.getCurrentCategoryProgress(), 0.01);

        CategoryProgressDTO category2Progress = result.getCategoryProgress().get(1);
        assertEquals("Category 2", category2Progress.getCategoryName());
        assertEquals(1, category2Progress.getAnsweredQuestions());
        assertEquals(1, category2Progress.getTotalQuestions());
        assertEquals(100.0, category2Progress.getCurrentCategoryProgress(), 0.01);
    }

    /**
     * Tests the calculation of audit progress when there are no ratings.
     *
     * Verifies that the progress is correctly set to 0 for all metrics.
     */
    @Test
    void testCalculateAuditProgressWithNoRatings() {
        when(ratingRepository.findByAuditId(2L)).thenReturn(new ArrayList<>());

        AuditProgressDTO result = auditProgressService.calculateAuditProgress(2L);

        assertEquals(0.0, result.getCurrentAuditProgress(), 0.01);
        assertEquals(0, result.getCategoryProgress().size());
    }

    /**
     * Tests the calculation of audit progress with null 'NA' values in ratings.
     *
     * Verifies that the progress is calculated correctly, including overall progress,
     * per-category progress, and question counts by ratings.
     */
    @Test
    void testCalculateAuditProgressWithNullNaValues() {
        Rating ratingWithNullNa = new Rating();
        ratingWithNullNa.setPoints(3);
        ratingWithNullNa.setNa(null);
        ratingWithNullNa.setQuestion(ratings.get(0).getQuestion());

        ratings.add(ratingWithNullNa);
        when(ratingRepository.findByAuditId(3L)).thenReturn(ratings);

        AuditProgressDTO result = auditProgressService.calculateAuditProgress(3L);

        assertEquals(2, result.getCategoryProgress().size());
        assertEquals(90.0, result.getCurrentAuditProgress(), 0.01);
    }
}
