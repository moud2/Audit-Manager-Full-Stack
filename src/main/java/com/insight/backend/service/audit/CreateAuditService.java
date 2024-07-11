package com.insight.backend.service.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.rating.SaveRatingService;

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
     *
     * @param newAuditDTO the DTO containing the details of the new audit
     * @return an AuditResponseDTO containing the details of the created audit,
     *         or null if any of the provided category IDs are invalid
     */
    public AuditResponseDTO createAudit(NewAuditDTO newAuditDTO) {
        for (Long categoryId : newAuditDTO.getCategories()) {
            if (!isCategoryValid(categoryId)) {
                return null;
            }
        }

        Audit audit = new Audit();
        audit.setName(newAuditDTO.getName());

        List<Rating> ratings = new ArrayList<>();

        // Add questions to the audit and create ratings for each question
        for (Long categoryId : newAuditDTO.getCategories()) {
            Optional<Category> categoryOpt = findCategoryService.findCategoryById(categoryId);
            if (categoryOpt.isPresent()) {
                Category category = categoryOpt.get();
                for (Question question : category.getQuestions()) {
                    Rating rating = new Rating();
                    rating.setQuestion(question);
                    rating.setAudit(audit);
                    ratings.add(rating);
                }
            }
        }

        saveRatingService.saveAllRatings(ratings);
        saveAuditService.saveAudit(audit);

        AuditResponseDTO auditResponseDTO = new AuditResponseDTO();
        auditResponseDTO.setId(audit.getId());
        auditResponseDTO.setName(audit.getName());

        return auditResponseDTO;
    }

    /**
     * Checks if a category with the given ID exists.
     *
     * @param categoryId the ID of the category to check
     * @return true if the category exists, false otherwise
     */
    private boolean isCategoryValid(Long categoryId) {
        return findCategoryService.findCategoryById(categoryId).isPresent();
    }
}