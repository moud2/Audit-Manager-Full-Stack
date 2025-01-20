package com.insight.backend.service;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import com.insight.backend.model.Audit;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.rating.PdfGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PdfGeneratorServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private PdfGeneratorService pdfGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePdf() {
        // Mock an Audit
        Audit mockAudit = new Audit();
        mockAudit.setId(1L);
        mockAudit.setName("Sample Audit");

        //Mock a Question
        Question mockQuestion = new Question();
        mockQuestion.setId(1L);
        mockQuestion.setName("Sample Question");

        // Mock a Rating
        Rating mockRating = new Rating();
        mockRating.setId(1L);
        mockRating.setPoints(5);
        mockRating.setComment("Great performance");
        mockRating.setNa(false);
        mockRating.setQuestion(mockQuestion);
        // Link Rating to Audit
        Set<Rating> ratings = new HashSet<>();
        ratings.add(mockRating);
        mockAudit.setRatings(ratings);

        // Mock Repository Behavior
        when(auditRepository.findById(1L)).thenReturn(java.util.Optional.of(mockAudit));

        // Call the Service
        ByteArrayInputStream result = pdfGeneratorService.createPdf(1L);

        // Verify Result
        assertNotNull(result, "The PDF generation should return a valid ByteArrayInputStream");
    }
}