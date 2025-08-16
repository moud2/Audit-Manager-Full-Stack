package com.insight.backend.service.audit;

import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Audit;
import com.insight.backend.repository.AuditRepository;
import com.insight.backend.specifications.AuditSpecifications;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
     * @return an optional object with the audit with the specified ID, or an optional with no audit if not found
     */
    public Optional<Audit> findAuditById(Long id) {
        Optional<Audit> optionalAudit = auditRepository.findById(id);
        if (optionalAudit.isPresent()) {
            Audit audit = optionalAudit.get();
            if (audit.getDeletedAt() != null) {
                return Optional.empty();
            }
        }
        return optionalAudit;
    }

    /**
     * Finds all audits.
     * 
     * @return a list of all audits
     */
    public List<Audit> findAllAudits() {
        return auditRepository.findAll();
    }

    /**
     * Finds all audits with the specified search string.
     *
     * @param search the string to search for in the name or customer
     * @param sortDirection the direction to sort the results
     * @return a list of all audits with the specified search string
     */
    public List<Audit> findAllAudits(String search, String sortDirection, String sortBy) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Specification<Audit> specification = Specification.where(AuditSpecifications.isNotDeleted()).and(AuditSpecifications.nameOrCustomerContains(search));
        return auditRepository.findAll(specification, sort);
    }
}
