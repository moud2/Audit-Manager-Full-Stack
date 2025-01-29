package com.insight.backend.service.audit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.exception.DuplicateCategoryIdException;
import com.insight.backend.exception.NonExistentAuditCategoryException;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.rating.SaveRatingService;

import org.springframework.stereotype.Service;
/**
 * Service class for creating audits.
 */
@Service
public class CreateAuditService {

    private final FindCategoryService findCategoryService;
    private final SaveAuditService saveAuditService;
    private final SaveRatingService saveRatingService;

    /**
     * Constructs a CreateAuditService with the specified services.
     *
     * @param findCategoryService the service to check category existence
     * @param saveAuditService the service to save audits
     * @param saveRatingService the service to save a list of ratings
     */
    public CreateAuditService(FindCategoryService findCategoryService, SaveAuditService saveAuditService, SaveRatingService saveRatingService) {
        this.findCategoryService = findCategoryService;
        this.saveAuditService = saveAuditService;
        this.saveRatingService = saveRatingService;
    }

    /**
     * Creates a new audit based on the provided NewAuditDTO.
     * This method also adds questions to the audit and creates a rating for each question.
     * This method also adds a timestamp to the audit creation.
     *
     * @param newAuditDTO the DTO containing the details of the new audit
     * @return an AuditResponseDTO containing the details of the created audit
     * @throws NonExistentAuditCategoryException if any of the provided category IDs are invalid
     */
    public AuditResponseDTO createAudit(NewAuditDTO newAuditDTO) {
        // Validate for duplicate Category-IDs
        if (newAuditDTO.getCategories().size() != new HashSet<>(newAuditDTO.getCategories()).size()) {
            throw new DuplicateCategoryIdException();
        }
        Audit audit = new Audit();
        audit.setName(newAuditDTO.getName());
        audit.setCustomer(newAuditDTO.getCustomer());

        List<Rating> ratings = new ArrayList<>();

        // Add questions to the audit and create ratings for each question
        for (Long categoryId : newAuditDTO.getCategories()) {
            Category category = findCategoryService.findCategoryById(categoryId).orElseThrow(()-> new NonExistentAuditCategoryException(categoryId));

            for (Question question : category.getQuestions()) {
                if (question.getDeletedAt() != null) {
                    continue;
                }

                Rating rating = new Rating();
                rating.setQuestion(question);
                rating.setAudit(audit);
                ratings.add(rating);
            }
        }

        saveAuditService.saveAudit(audit);
        saveRatingService.saveAllRatings(ratings);

        AuditResponseDTO auditResponseDTO = new AuditResponseDTO();
        auditResponseDTO.setId(audit.getId());
        auditResponseDTO.setName(audit.getName());
        auditResponseDTO.setCreatedAt(audit.getCreatedAt());
        auditResponseDTO.setCustomer(audit.getCustomer());

        return auditResponseDTO;
    }
}