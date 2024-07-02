package com.insight.backend.service.Audit;

import java.util.*;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;

import org.springframework.stereotype.Service;

/**
 * Service class for finding audits.
 */
@Service
public class FindAuditService {

    private AuditRepository auditRepository;

    /**
     * Constructs a new findAuditService with the specified AuditRepository.
     *
     * @param auditRepository the repository to use for audit operations
     */
    public FindAuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**
     * Finds an audit by its ID.
     * 
     * @param id the ID of the audit
     * @return the audit with the specified ID, or null if not found
     */
    public Optional<Audit> findAuditById(Long id) {
        return auditRepository.findById(id);
    }

    /**
     * Finds all audits.
     * 
     * @return a list of all audits
     */
    public List<Audit> findAllAudits() {
        return auditRepository.findAll();
    }
}
