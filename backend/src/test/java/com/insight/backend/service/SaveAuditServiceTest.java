package com.insight.backend.service;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.audit.SaveAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        audit.setCustomer("CustomerTest");
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
    }

    /*
     * Test method for saving with null object, tests for actual saved audit being null as well
     */
    @Test
    public void saveNullAuditTest() {
        assertNull(saveAuditService.saveAudit(null));
    }

    @Test
    public void testConstructor() {
        SaveAuditService saveAuditServiceCons = new SaveAuditService(auditRepository);

        assertNotNull(saveAuditServiceCons);
    }
}
