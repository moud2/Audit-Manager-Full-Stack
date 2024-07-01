package com.insight.backend.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.Audit.FindAuditService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        Audit foundAudit = findAuditService.findAuditById(1L);

        assertEquals(audit1, foundAudit);
    }

    @Test
    void testFindAuditById_notFound() {
        when(auditRepository.findById(anyLong())).thenReturn(Optional.empty());

        Audit foundAudit = findAuditService.findAuditById(1L);

        assertNull(foundAudit);
    }

    @Test
    void testFindAllAudits() {
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(auditRepository.findAll()).thenReturn(audits);

        List<Audit> foundAudits = findAuditService.findAllAudits();

        assertEquals(audits, foundAudits);
    }
}
