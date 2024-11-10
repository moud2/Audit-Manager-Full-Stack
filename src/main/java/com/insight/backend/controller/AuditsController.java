package com.insight.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.ErrorDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.model.Audit;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.dto.AuditProgressDTO;
import com.insight.backend.service.audit.AuditProgressService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * AuditsController is a REST controller that handles HTTP requests related to audits.
 */
@RestController
public class AuditsController {

    /** 
     * The FindAuditService to use the service methods.
     */
    private final FindAuditService findAuditService;
    private final CreateAuditService createAuditService;

    /**
     * The AuditProgressService to use the service methods.
     */
    private final AuditProgressService auditProgressService;

    /**
     * Constructs a new AuditsController with the specified FindAuditService.
     * 
     * @param findAuditService the service to find audits
     */
    @Autowired
    public AuditsController(FindAuditService findAuditService, CreateAuditService createAuditService, AuditProgressService auditProgressService) {
        this.findAuditService = findAuditService;
        this.createAuditService = createAuditService;
        this.auditProgressService = auditProgressService;
    }

    /**
     * GET requests for retrieving all audits.
     * 
     * @return a ResponseEntity containing a list of Audit objects
     */
    @GetMapping("api/v1/audits")
    public ResponseEntity<List<Audit>> getAudits() {
        List<Audit> response = findAuditService.findAllAudits();

        return ResponseEntity.ok(response);
    }

    /**
     * POST requests for creating new Audit.
     *
     * @return a ResponseEntity containing an ID and name of new Audit
     */

    @PostMapping("/api/v1/audits/new")
    public ResponseEntity<Object> postWithRequestBody(@Valid @RequestBody NewAuditDTO newAuditDTO) {

        AuditResponseDTO responseDTO = createAuditService.createAudit(newAuditDTO);

        if (responseDTO == null) {
            ErrorDTO errorDTO = new ErrorDTO("non existing category provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * GET requests for retrieving overall progress of one audit.
     *
     * @return a ResponseEntity containing percentage of progress
     */
    @GetMapping("/api/v1/audits/{auditId}/progress")
    public ResponseEntity<AuditProgressDTO> getAuditProgress(@PathVariable Long auditId) {
        AuditProgressDTO progressDTO = auditProgressService.calculateAuditProgress(auditId);
        return ResponseEntity.ok(progressDTO);
    }
}