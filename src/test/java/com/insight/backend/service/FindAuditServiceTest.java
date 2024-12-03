package com.insight.backend.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.service.audit.FindAuditService;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;


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
    private Audit audit3;
    private Audit audit4;

    @BeforeEach
    void setUp() {
        audit1 = new Audit();
        audit1.setId(1L);
        audit1.setName("Audit1");
        audit1.setCustomer("Customer1");

        audit2 = new Audit();
        audit2.setId(2L);
        audit2.setName("Audit2");
        audit2.setCustomer("Customer2");

        audit3 = new Audit();
        audit3.setId(3L);
        audit3.setName("Audit3");
        audit3.setCustomer("abc");

        audit4 = new Audit();
        audit4.setId(4L);
        audit4.setName("Audit4");
        audit4.setCustomer(null);
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
        audit2.setDeletedAt(LocalDateTime.now());
        List<Audit> mockAudits = Arrays.asList(audit1, audit3, audit4);
        
        when(auditRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(mockAudits);
        
        List<Audit> result = findAuditService.findAllAudits("Customer", "asc", "name");
        assertEquals(3, result.size());
        assertEquals("Audit1", result.getLast().getName());

    }
}
