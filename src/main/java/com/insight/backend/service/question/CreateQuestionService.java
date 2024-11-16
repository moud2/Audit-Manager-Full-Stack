package com.insight.backend.service.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.category.FindQuestionByCategoryService;
import com.insight.backend.service.rating.SaveRatingService;
import com.insight.backend.service.question.SaveQuestionService;
import com.insight.backend.exception.QuestionNotFoundException;
import com.insight.backend.exception.CategoryNotFoundException;


import org.springframework.stereotype.Service;

/**
 * Service class for creating audits.
 */
@Service
public class CreateQuestionService {

    private final FindCategoryService findCategoryService;
    private final SaveQuestionService saveQuestionService;
    private final SaveRatingService saveRatingService;
    private final FindQuestionByCategoryService findQuestion;

    /**
     * Constructs a CreateAuditService with the specified services.
     *
     * @param findCategoryService the service to check category existence
     * @param saveQuestionService the service to save audits
     * @param saveRatingService the service to save a list of ratings
     */
    public CreateQuestionService(FindCategoryService findCategoryService, SaveQuestionService saveQuestionService, SaveRatingService saveRatingService, FindQuestionByCategoryService findQuestionService) {
        this.findCategoryService = findCategoryService;
        this.saveQuestionService = saveQuestionService;
        this.saveRatingService = saveRatingService;
        this.findQuestionService = findQuestionService;
    }

    /**
     * Creates a new audit based on the provided NewQuestionDTO.
     * 
     *
     * @param newQuestionDTO the DTO containing the details of the new Question
     * @return an AuditResponseDTO containing the details of the created Question,
     *         or null if any of the provided category IDs are invalid
     */
    public QuestionResponseDTO createQuestion(NewQuestionDTO newQuestionDTO) {
        Question question = new Question();
        question.setName(newQuestionDTO.getName());

        // hier gibt es noch keine suche nach namen
        /*Optional<Question> questionOpt = findQuestionService.findQuestionById(newQuestionDTO.getName());
        if (questionOpt.isPresent()) {
            question.setName(newQuestionDTO.getName());
        } else throw new QuestionNotFoundException(); */
        
        Optional<Category> categoryOpt = findCategoryService.findCategoryById(newQuestionDTO.getCategory().getId());
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            question.setCategory(category);
        } else throw new CategoryNotFoundException(); 

        saveQuestionService.saveQuestion(question);

        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(question.getId());
        questionResponseDTO.setName(question.getName());

        return questionResponseDTO;
    }
}