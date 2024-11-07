package com.insight.backend.service.audit;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * Service class for deleting audits.
 */
@Service
public class DeleteAuditService {

    private AuditRepository auditRepository;

    /**
     * Constructs a new deletAuditService with the specified AuditRepository.
     *
     * @param auditRepository the repository to use for audit operations
     */
    public DeleteAuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void softDeleteAudit(Audit audit) {
        if (audit != null) {
            audit.setDeletedAt(LocalDateTime.now());
        }
        auditRepository.saveAndFlush(audit);
    }
}