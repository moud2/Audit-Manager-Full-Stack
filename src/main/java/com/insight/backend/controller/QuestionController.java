package com.insight.backend.controller;

import java.util.Optional;
import java.util.List;

import com.insight.backend.exception.CategoryNotFoundException;
import jakarta.validation.Valid;

import com.insight.backend.model.Question;
import com.insight.backend.model.Category;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.SaveQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.dto.ErrorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class QuestionController {
    private final DeleteQuestionService deleteQuestionService;
    private final FindQuestionByCategoryService findQuestionService;
    private final FindCategoryService findCategoryService;
    private final CreateQuestionService createQuestionService;

    /**
     * Constructs a new QuestionController with the DeleteQuestionService and
     * FindQuestionsByCategoryService
     * 
     * @param deleteQuestionService the service to delete a question
     * 
     */
    @Autowired
    public QuestionController(DeleteQuestionService deleteQuestionService, CreateQuestionService createQuestionService, FindQuestionByCategoryService findQuestionService, FindCategoryService findCategoryService) {
        this.deleteQuestionService = deleteQuestionService; 
        this.findQuestionService = findQuestionService;
        this.findCategoryService = findCategoryService;
        this.createQuestionService = createQuestionService;
    }

    @DeleteMapping("/api/v1/questions/{questionID}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionID") Long questionID){
        Optional<Question> optionalQuestion = findQuestionService.findQuestionByID(questionID);
        if(optionalQuestion.isPresent()){
            deleteQuestionService.deleteQuestion(questionID);
            return ResponseEntity.ok("Question succesfully deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }

    @PostMapping("/api/v1/questions/new")
    public ResponseEntity<Object> postWithRequestBody(@Valid @RequestBody NewQuestionDTO newQuestionDTO) {
        QuestionResponseDTO responseDTO = this.createQuestionService.createQuestion(newQuestionDTO);

        if (responseDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } else return ResponseEntity.badRequest().body(null);

    }

    @GetMapping("/api/v1/categories/{categoryId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("categoryID") Long categoryID, @RequestParam(required = false, defaultValue = "asc") String sortDirection, @RequestParam(required = false, defaultValue = "id") String sortBy){
        Optional<Category> optionalCategory = findCategoryService.findCategoryById(categoryID);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            List<Question> questions = findQuestionService.findQuestionsByCategory(category, sortDirection, sortBy);
            return ResponseEntity.ok(questions);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
