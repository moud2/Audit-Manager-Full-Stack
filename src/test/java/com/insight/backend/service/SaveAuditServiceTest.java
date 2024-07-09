package com.insight.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.audit.SaveAuditService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveAuditServiceTest {
    
    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private SaveAuditService saveAuditService;

    private Audit audit;

    /*
    * Test data to create before execution of and be used in single test method
     */
    @BeforeEach
    public void setUp() {
        audit = new Audit();
        audit.setId(1L);
        audit.setName("AuditTest");
    }

    /*
    * Test method for saving audit, compares audit to be saved with actual saved audit
    */
    @Test
    public void saveAuditTest() {
        when(auditRepository.saveAndFlush(audit)).thenReturn(audit);
        Audit savedAudit = saveAuditService.saveAudit(audit);
        verify(auditRepository, times(1)).saveAndFlush(audit);
        assertNotNull(savedAudit);
        assertEquals(savedAudit, audit);
        assertEquals(savedAudit.getId(), audit.getId());
        assertEquals(savedAudit.getName(), audit.getName());
        assertEquals(savedAudit.getRatings(), audit.getRatings());
    }

    /*
     * Test method for saving with null object, tests for actual saved audit being null as well
     */
    @Test
    public void saveNullAuditTest() {
        assertNull(saveAuditService.saveAudit(null));
    }
}
