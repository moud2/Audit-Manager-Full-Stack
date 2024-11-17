package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.model.Question;
import com.insight.backend.model.Category;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.service.audit.AuditProgressService;
import com.insight.backend.dto.AuditProgressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * test class for testing AuditsController
 */
@WebMvcTest(AuditsController.class)
@ExtendWith(SpringExtension.class)
public class AuditControllerTestHttpGet {

    /**
     * MockMvc instance for HTTP request mocking
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditProgressService auditProgressService;

    @MockBean
    private FindAuditService findAuditService;

    @MockBean
    private CreateAuditService createAuditService;

    private Audit audit1;
    private Audit audit2;
    private Question question1;
    private Question question2;
    private Rating rating5Points;
    private Rating ratingNATrue;
    private Category category1;
    private Category category2;

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    public void setup() {
        audit1 = new Audit();
        audit1.setName("TestAudit1");
        audit1.setId(1L);

        audit2 = new Audit();
        audit2.setName("TestAudit2");
        audit2.setId(2L);

        question1 = new Question();
        category1 = new Category();
        rating5Points = new Rating();

        question2 = new Question();
        category2 = new Category();
        ratingNATrue = new Rating();

        Set<Rating> ratings1 = new HashSet<>();
        ratings1.add(rating5Points);
        question1.setName("erste Frage");
        question1.setRating(ratings1);
        question1.setCategory(category1);

        Set<Rating> ratings2 = new HashSet<>();
        ratings2.add(ratingNATrue);
        question2.setName("zweite Frage");
        question2.setRating(ratings2);
        question2.setCategory(category2);

        category1.setName("Kategorie 1");
        category2.setName("Kategorie 2");

        rating5Points.setId(1L);
        rating5Points.setNa(false);
        rating5Points.setComment("1");
        rating5Points.setQuestion(question1);
        rating5Points.setAudit(audit1);

        ratingNATrue.setId(2L);
        ratingNATrue.setNa(true);
        ratingNATrue.setComment("2");
        ratingNATrue.setQuestion(question2);
        ratingNATrue.setAudit(audit1);
    }

    /**
     * Tests getting all available audits.
     * 
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllAudits() throws Exception {
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(findAuditService.findAllAudits()).thenReturn(audits);

        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestAudit1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestAudit2"));
    }

    /**
     * Test gettin an empty list of audits.
     * 
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetEmpties() throws Exception {
        List<Audit> audits = new ArrayList<>();
        when(findAuditService.findAllAudits()).thenReturn(audits);

        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Tests retrieving the progress of an existing audit.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAuditProgressSuccess() throws Exception {
        Long auditId = 1L;

        AuditProgressDTO progressDTO = new AuditProgressDTO();
        progressDTO.setAuditId(auditId);
        progressDTO.setOverallProgress(75.0);
        progressDTO.setCategoryProgress(Map.of("Kategorie 1", 80.0, "Kategorie 2", 70.0));
        progressDTO.setQuestionCountByRating(Map.of("5", 1L, "nA", 1L));

        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.of(audit1));
        when(auditProgressService.calculateAuditProgress(auditId)).thenReturn(progressDTO);

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditId").value(auditId))
                .andExpect(jsonPath("$.overallProgress").value(75.0))
                .andExpect(jsonPath("$.categoryProgress['Kategorie 1']").value(80.0))
                .andExpect(jsonPath("$.categoryProgress['Kategorie 2']").value(70.0))
                .andExpect(jsonPath("$.questionCountByRating.5").value(1))
                .andExpect(jsonPath("$.questionCountByRating.nA").value(1));
    }

    /**
     * Tests retrieving progress when the audit does not exist.
     * Expects a 404 Not Found status.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAuditProgressNotFound() throws Exception {
        Long auditId = 999L;

        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audit not found or has been deleted"));
    }

    /**
     * Tests retrieving progress when the audit is soft-deleted.
     * Expects a 404 Not Found status.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAuditProgressSoftDeleted() throws Exception {
        Long auditId = 1L;

        audit1.setDeletedAt(java.time.LocalDateTime.now());
        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.of(audit1));

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audit not found or has been deleted"));
    }

    /**
     * Tests retrieving progress for an audit with no data (empty progress).
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAuditProgressEmptyData() throws Exception {
        Long auditId = 1L;

        AuditProgressDTO progressDTO = new AuditProgressDTO();
        progressDTO.setAuditId(auditId);
        progressDTO.setOverallProgress(0.0);
        progressDTO.setCategoryProgress(Map.of());
        progressDTO.setQuestionCountByRating(Map.of());

        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.of(audit1));
        when(auditProgressService.calculateAuditProgress(auditId)).thenReturn(progressDTO);

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditId").value(auditId))
                .andExpect(jsonPath("$.overallProgress").value(0.0))
                .andExpect(jsonPath("$.categoryProgress").isEmpty())
                .andExpect(jsonPath("$.questionCountByRating").isEmpty());
    }
}
