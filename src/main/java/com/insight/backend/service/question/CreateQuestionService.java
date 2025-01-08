package com.insight.backend.service.question;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.exception.CategoryNotFoundException;
import com.insight.backend.exception.QuestionFoundException;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.category.SaveCategoryService;

/**
 * Service class for creating questions.
 */
@Service
public class CreateQuestionService {

    private final FindCategoryService findCategoryService;
    private final SaveQuestionService saveQuestionService;
    private final SaveCategoryService saveCategoryService;
    private final FindQuestionByCategoryService findQuestionService;

    /**
     * Constructs a CreateAuditService with the specified services.
     *
     * @param findCategoryService the service to check category existence
     * @param saveQuestionService the service to save questions
     * @param saveCategoryService the service to save a list of ratings
     * @param findQuestionService 
     */
    public CreateQuestionService(FindCategoryService findCategoryService, SaveQuestionService saveQuestionService, SaveCategoryService saveCategoryService, FindQuestionByCategoryService findQuestionService) {
        this.findCategoryService = findCategoryService;
        this.saveQuestionService = saveQuestionService;
        this.saveCategoryService = saveCategoryService;
        this.findQuestionService = findQuestionService;
    }

    /**
     * Creates a new question based on the provided NewQuestionDTO.
     * 
     *
     * @param newQuestionDTO the DTO containing the details of the new Question
     * @return an QuestionResponseDTO containing the details of the created Question,
     *         or null if any of the provided category IDs are invalid
     */
    public QuestionResponseDTO createQuestion(NewQuestionDTO newQuestionDTO) {
        Question question = new Question();
        question.setName(newQuestionDTO.getName());

        List<Question> questionOpt = this.findQuestionService.findQuestionsByName(newQuestionDTO.getName(), "desc", "name");
        if (questionOpt.isEmpty()) {
            question.setName(newQuestionDTO.getName());
        } else throw new QuestionFoundException();
                
        //List<Category> categoryOpt = this.findCategoryService.findAllCategories().stream().filter(x -> x.getName().equals(newQuestionDTO.getCategory())).collect(Collectors.toList());
        Category category = this.findCategoryService.findCategoryById(newQuestionDTO.getCategoryId()).orElseThrow(() -> {
                System.out.println("Category ID not found: " + newQuestionDTO.getCategoryId());
                return new CategoryNotFoundException(newQuestionDTO.getCategoryId());
        });
        question.setCategory(category);


        //Categories um die neue question aktualisieren
        //Categories werden nur verknüpft glaube nicht notwendig zu speichern da sie nicht geändert werden 
        /**Set<Question> tmpQuestionList = category.getQuestions();
        tmpQuestionList.add(question);
        category.setQuestions(tmpQuestionList);**/
        saveQuestionService.saveQuestion(question);
        //saveCategoryService.saveCategory(category);

        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(question.getId());
        questionResponseDTO.setName(question.getName());

        return questionResponseDTO;
    }
}