package com.insight.backend.controller;

import java.util.Optional;

import com.insight.backend.model.Audit;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.DeleteAuditService;
import com.insight.backend.service.audit.FindAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditsController.class)
public class AuditsControllerTestHttpDelete {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindAuditService findAuditService;
    @MockBean
    private CreateAuditService createAuditService;
    @MockBean
    private DeleteAuditService deleteAuditService;
    
    @BeforeEach
    public void setUp() {
        audit = new Audit();
        audit.setId(1L);
        audit.setDeletedAt(null);
        audit.setName("AuditToDelete");
    }

    private Audit audit;

    @Test
    public void testSoftDeleteExistingAudit() throws Exception {
            // Mock the service to return the Audit
            when(findAuditService.findAuditById(1L)).thenReturn(Optional.of(audit));

            // Perform the Delete Request with java spring boot delete
            mockMvc.perform(delete("/api/v1/audits/{auditId}", 1L))
                .andExpect(status().isNoContent())
                .andReturn();

        // delete should be called once
        verify(deleteAuditService, times(1)).softDeleteAudit(audit);
    }

    @Test
    public void testSoftDeleteNotExistingAudit() throws Exception {
        // Mock the service to return no Audit
        when(findAuditService.findAuditById(1L)).thenReturn(Optional.empty());

        // Perform the Delete Request with java spring boot delete
        mockMvc.perform(delete("/api/v1/audits/{auditId}", 1L))
                .andExpect(status().isNotFound())
                .andReturn();

        // delete should not be called
        verify(deleteAuditService, times(0)).softDeleteAudit(audit);
    }
}
