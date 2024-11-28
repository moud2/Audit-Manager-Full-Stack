package com.insight.backend.service.audit;

import java.util.ArrayList;
import java.util.List;

import com.insight.backend.dto.AuditProgressDTO;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

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
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setName("Category 2");

        Question question1 = new Question();
        question1.setName("Question 1");
        question1.setCategory(category1);

        Question question2 = new Question();
        question2.setName("Question 2");
        question2.setCategory(category2);

        Rating rating1 = new Rating();
        rating1.setPoints(4);
        rating1.setNa(false);
        rating1.setQuestion(question1);

        Rating rating2 = new Rating();
        rating2.setPoints(5);
        rating2.setNa(false);
        rating2.setQuestion(question2);

        Rating ratingNa = new Rating();
        ratingNa.setNa(true);
        ratingNa.setQuestion(question1);

        ratings = new ArrayList<>();
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(ratingNa);
    }

    /**
     * Tests the calculation of audit progress with valid ratings data.
     *
     * Verifies that the progress is calculated correctly, including overall progress,
     * per-category progress, and question counts by ratings.
     */
    @Test
    void testCalculateAuditProgress() {
        Long auditId = 1L;

        // Mock repository response
        when(ratingRepository.findByAuditId(auditId)).thenReturn(ratings);

        // Call the service method
        AuditProgressDTO progressDTO = auditProgressService.calculateAuditProgress(auditId);

        // Assert results
        assertEquals(auditId, progressDTO.getAuditId());
        assertEquals(90.0, progressDTO.getOverallProgress(), 0.01);

        // Verify category progress
        assertEquals(80.0, progressDTO.getCategoryProgress().get("Category 1"), 0.01);
        assertEquals(100.0, progressDTO.getCategoryProgress().get("Category 2"), 0.01);

        // Verify question count by rating
        assertEquals(1L, progressDTO.getQuestionCountByRating().get("4"));
        assertEquals(1L, progressDTO.getQuestionCountByRating().get("5"));
        assertEquals(1L, progressDTO.getQuestionCountByRating().get("nA"));
    }

    /**
     * Tests the calculation of audit progress when there are no ratings.
     *
     * Verifies that the progress is correctly set to 0 for all metrics.
     */
    @Test
    void testCalculateAuditProgressWithNoRatings() {
        Long auditId = 2L;

        // Mock repository response to return an empty list
        when(ratingRepository.findByAuditId(auditId)).thenReturn(new ArrayList<>());

        // Call the service method
        AuditProgressDTO progressDTO = auditProgressService.calculateAuditProgress(auditId);

        // Assert results
        assertEquals(auditId, progressDTO.getAuditId());
        assertEquals(0.0, progressDTO.getOverallProgress(), 0.01);
        assertEquals(0, progressDTO.getCategoryProgress().size());
        assertEquals(0L, progressDTO.getQuestionCountByRating().values().stream().mapToLong(Long::longValue).sum());
    }
}
