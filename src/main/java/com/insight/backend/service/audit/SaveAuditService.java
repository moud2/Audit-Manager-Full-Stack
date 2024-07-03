package com.insight.backend.service.audit;

import java.util.HashSet;
import java.util.Set;

import com.insight.backend.repository.AuditRepository;
import com.insight.backend.model.Audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveAuditService {
    
    
    private final AuditRepository auditRepository;

    public SaveAuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /*
     * save Audit and return it afterwards
     * @param audit to be saved
     * @return saved audit
     */
    public Audit saveAudit(Audit audit){
        if(audit == null) return null;
        return auditRepository.saveAndFlush(audit);
    }
}