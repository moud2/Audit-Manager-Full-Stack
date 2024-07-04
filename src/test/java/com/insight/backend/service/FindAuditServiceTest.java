package com.insight.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.audit.FindAuditService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private FindAuditService findAuditService;

    private Audit audit1;
    private Audit audit2;

    @BeforeEach
    void setUp() {
        audit1 = new Audit();
        audit1.setId(1L);
        audit1.setName("Audit1");

        audit2 = new Audit();
        audit2.setId(2L);
        audit2.setName("Audit2");
    }

    @Test
    void testFindAuditById_found() {
        when(auditRepository.findById(1L)).thenReturn(Optional.of(audit1));

        Optional<Audit> foundAudit = findAuditService.findAuditById(1L);

        assertEquals(audit1, foundAudit.get());
    }

    @Test
    void testFindAuditById_notFound() {
        when(auditRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Audit> foundAudit = findAuditService.findAuditById(1L);

        assertTrue(foundAudit.isEmpty());
    }

    @Test
    void testFindAllAudits() {
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(auditRepository.findAll()).thenReturn(audits);

        List<Audit> foundAudits = findAuditService.findAllAudits();

        assertEquals(audits, foundAudits);
    }
}
