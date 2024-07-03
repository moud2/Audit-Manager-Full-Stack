package com.insight.backend.service.Audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.Audit.SaveAuditService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @BeforeEach
    public void setUp() {
        audit = new Audit();
        audit.setId(1L);
        audit.setName("AuditTest");
    }

    @Test
    public void saveAuditTest() {
        when(auditRepository.saveAndFlush(audit)).thenReturn(audit);
        Audit savedAudit = saveAuditService.saveAudit(audit);
        verify(auditRepository, times(1)).saveAndFlush(audit);
        assertNotNull(savedAudit);
        assertEquals(savedAudit.getName(), audit.getName());
    }

    @Test
    public void saveNullAuditTest() {
        assertNull(saveAuditService.saveAudit(null));
    }
    
}
