package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.insight.backend.dto.ErrorDTO;
import com.insight.backend.dto.RatingDTO;
import com.insight.backend.exception.RatingNotFoundException;
import com.insight.backend.mapper.RatingMapper;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.service.rating.FindRatingService;
import com.insight.backend.service.rating.SaveRatingService;

@RestController
public class RatingController {

    private final ObjectMapper objectMapper;
    private final FindRatingService findRatingService;
    private final SaveRatingService saveRatingService;
    private final FindAuditService findAuditService;
    private final RatingMapper ratingMapper;

    /**
     * Constructs a RatingController with the specified services and mapper.
     *
     * @param objectMapper the object mapper for JSON processing
     * @param findRatingService the service to find ratings
     * @param saveRatingService the service to save ratings
     * @param findAuditService the service to find audits
     * @param ratingMapper the mapper to convert Rating to RatingDTO
     */
    @Autowired
    public RatingController(ObjectMapper objectMapper, FindRatingService findRatingService, SaveRatingService saveRatingService, FindAuditService findAuditService, RatingMapper ratingMapper) {
        this.objectMapper = objectMapper;
        this.findRatingService = findRatingService;
        this.saveRatingService = saveRatingService;
        this.findAuditService = findAuditService;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Handles PATCH requests for updating a rating.
     *
     * @param id    the ID of the rating to update
     * @param patch the JSON patch containing the changes to apply
     * @return a ResponseEntity containing the updated rating in JSON format or an error message if the rating ID does not exist
     */
    @PatchMapping("/api/v1/ratings/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable("id") long id, @RequestBody JsonPatch patch) {
        try {
            Rating entity = findRatingService.findRatingById(id).orElseThrow(RatingNotFoundException::new);
            JsonNode entityJsonNode;
            entityJsonNode = objectMapper.convertValue(entity, JsonNode.class);
            JsonNode patchedEntityJsonNode = patch.apply(entityJsonNode);
            Rating patchedEntity = objectMapper.treeToValue(patchedEntityJsonNode, Rating.class);
            Rating updatedEntity = saveRatingService.saveRating(patchedEntity);
            return ResponseEntity.ok(updatedEntity);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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