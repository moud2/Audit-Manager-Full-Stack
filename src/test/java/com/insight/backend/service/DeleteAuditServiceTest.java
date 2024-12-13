package com.insight.backend.service;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.audit.DeleteAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAuditServiceTest {
    
    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private DeleteAuditService deleteAuditService;

    private Audit audit;
    private Audit nullAudit;

    /*
    * Test data to create before execution of and be used in single test method
     */
    @BeforeEach
    public void setUp() {
        audit = new Audit();
        audit.setId(1L);
        audit.setName("AuditTest");
        audit.setDeletedAt(null);
    }

    @Test
    public void softDeleteAuditTest() {
        
        deleteAuditService.softDeleteAudit(audit);;
       
        verify(auditRepository, times(1)).saveAndFlush(audit);

        assertNotEquals(audit.getDeletedAt(), null);
    }

    @Test
    public void softDeleteAuditNullTest() {
        
        deleteAuditService.softDeleteAudit(nullAudit);
       
        verify(auditRepository, times(0)).saveAndFlush(audit);
    }
}
