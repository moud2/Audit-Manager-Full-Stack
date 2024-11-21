package com.insight.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.exception.AuditNotFoundException;
import com.insight.backend.model.Audit;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.service.audit.DeleteAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final DeleteAuditService deleteAuditService;
    /**
     * Constructs a new AuditsController with the specified FindAuditService.
     * 
     * @param findAuditService the service to find audits
     */
    @Autowired
    public AuditsController(FindAuditService findAuditService, CreateAuditService createAuditService, DeleteAuditService deleteAuditService) {
        this.findAuditService = findAuditService;
        this.createAuditService = createAuditService;
        this.deleteAuditService = deleteAuditService;
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

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

        /**
     * Handles DELETE requests for deleting an audit.
     *
     * @param auditId    the ID of the audit to softdelete
     * @param patch the JSON patch containing the changes to apply
     * @return a ResponseEntity containing info about the delete operation in JSON format
     */
    // Soft Delete Endpoint
    @DeleteMapping("/api/v1/audits/{auditId}")
    public ResponseEntity<Object> softDeleteAudit(@PathVariable("auditId") Long auditId) {
        Audit auditToDelete = findAuditService.findAuditById(auditId).orElseThrow(() -> new AuditNotFoundException(auditId));
        try {
            deleteAuditService.softDeleteAudit(auditToDelete);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}