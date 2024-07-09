package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.insight.backend.dto.ErrorDTO;
import com.insight.backend.dto.RatingDTO;
import com.insight.backend.mapper.RatingMapper;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.service.audit.FindAuditService;

@RestController
public class RatingController {

    private final FindAuditService findAuditService;
    private final RatingMapper ratingMapper;
    
    /**
     * Constructs a RatingController with the specified services and mapper.
     *
     * @param findAuditService the service to find audits
     * @param ratingMapper the mapper to convert Rating to RatingDTO
     */
    public RatingController(FindAuditService findAuditService, RatingMapper ratingMapper) {
        this.findAuditService = findAuditService;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Retrieves the ratings for a specific audit.
     *
     * @param auditId the ID of the audit
     * @return a ResponseEntity containing a list of RatingDTOs or a bad request status
     */
    @GetMapping("/api/v1/audits/{auditId}/ratings")
    public ResponseEntity<Object> getRatings(@PathVariable("auditId") Long auditId) {
        Optional<Audit> optionalAudit = findAuditService.findAuditById(auditId);
        if (optionalAudit.isPresent()) {
            Audit audit = optionalAudit.get();
            List<Rating> ratings = new ArrayList<>(audit.getRatings());
            List<RatingDTO> ratingDTOs = ratingMapper.convertToRatingDTOs(ratings);
            return ResponseEntity.ok(ratingDTOs);
        } else {
            ErrorDTO errorResponse = new ErrorDTO("audit with id " + auditId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}