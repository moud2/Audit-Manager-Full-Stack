package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import com.insight.backend.dto.CategoryProgressDTO;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.model.Question;
import com.insight.backend.model.Category;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.DeleteAuditService;
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
import static org.mockito.ArgumentMatchers.any;
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

    /**
     * MockBean for FindAuditService
     */
    @MockBean
    private AuditProgressService auditProgressService;

    /**
     * MockBean for FindAuditService
     */
    @MockBean
    private FindAuditService findAuditService;

    /**
     * MockBean for CreateAuditService
     */
    @MockBean
    private CreateAuditService createAuditService;

    /**
     * MockBean for DeleteAuditService
     */
    @MockBean
    private DeleteAuditService deleteAuditService;

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

        audit1.setCreatedAt(java.time.LocalDateTime.now());
        audit2.setCreatedAt(java.time.LocalDateTime.now());
        audit1.setCustomer("TestCustomer1");
        audit2.setCustomer("TestCustomer2");

    }

    /**
     * Tests getting all available audits.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllAudits() throws Exception {
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(findAuditService.findAllAudits("", "asc", "id")).thenReturn(audits);

        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestAudit1"))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestAudit2"))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].name").value("TestAudit2"))
                .andExpect(jsonPath("$[0].customer").value("TestCustomer1"))
                .andExpect(jsonPath("$[1].customer").value("TestCustomer2"));
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

        AuditProgressDTO progressDTO = new AuditProgressDTO(
                auditId,
                50.0,
                List.of(
                        new CategoryProgressDTO(1L, "Kategorie 1", 5, 10, 50.0),
                        new CategoryProgressDTO(2L, "Kategorie 2", 3, 10, 30.0)
                )
        );

        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.of(audit1));
        when(auditProgressService.calculateAuditProgress(auditId)).thenReturn(progressDTO);

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditId").value(1))
                .andExpect(jsonPath("$.currentAuditProgress").value(50.0))
                .andExpect(jsonPath("$.categoryProgress", hasSize(2)))
                .andExpect(jsonPath("$.categoryProgress[0].categoryId").value(1))
                .andExpect(jsonPath("$.categoryProgress[0].categoryName").value("Kategorie 1"))
                .andExpect(jsonPath("$.categoryProgress[0].answeredQuestions").value(5))
                .andExpect(jsonPath("$.categoryProgress[0].totalQuestions").value(10))
                .andExpect(jsonPath("$.categoryProgress[0].currentCategoryProgress").value(50.0))
                .andExpect(jsonPath("$.categoryProgress[1].categoryId").value(2))
                .andExpect(jsonPath("$.categoryProgress[1].categoryName").value("Kategorie 2"))
                .andExpect(jsonPath("$.categoryProgress[1].answeredQuestions").value(3))
                .andExpect(jsonPath("$.categoryProgress[1].totalQuestions").value(10))
                .andExpect(jsonPath("$.categoryProgress[1].currentCategoryProgress").value(30.0));
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
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Audit with id " + auditId + " not found"));
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
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Audit with id " + auditId + " has been deleted"));
    }

    /**
     * Tests retrieving progress for an audit with no data (empty progress).
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAuditProgressEmptyData() throws Exception {
        Long auditId = 1L;

        AuditProgressDTO progressDTO = new AuditProgressDTO(auditId, 0.0, List.of());

        when(findAuditService.findAuditById(auditId)).thenReturn(Optional.of(audit1));
        when(auditProgressService.calculateAuditProgress(auditId)).thenReturn(progressDTO);

        mockMvc.perform(get("/api/v1/audits/{auditId}/progress", auditId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditId").value(1))
                .andExpect(jsonPath("$.currentAuditProgress").value(0.0))
                .andExpect(jsonPath("$.categoryProgress", hasSize(0)));
    }
}