package com.insight.backend.service.Audit;

import java.util.HashSet;
import java.util.Set;

import com.insight.backend.repository.AuditRepository;
import com.insight.backend.model.Audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveAuditService {
    
    
    private final AuditRepository auditRepository;

    public SaveAuditService(AuditRepository c) {
        this.auditRepository = c;
    }

    /*
     * create new audit using name
     * @param name of audit
     * @return newly created audit
     */
    public Audit saveAudit(Audit audit){
        if(audit == null) return null;
        auditRepository.saveAndFlush(audit);
        return audit;
    }
}