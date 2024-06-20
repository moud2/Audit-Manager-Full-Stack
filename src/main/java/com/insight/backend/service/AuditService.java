package com.insight.backend.service;

import java.util.List;

import com.insight.backend.repository.AuditRepository;
import com.insight.backend.model.Audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    @Autowired
    private final AuditRepository auditRepository;

    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }
}