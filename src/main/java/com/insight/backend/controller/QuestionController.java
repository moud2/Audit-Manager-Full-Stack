package com.insight.backend.controller;

import java.util.Optional;
import java.util.List;

import com.insight.backend.model.Question;
import com.insight.backend.model.Category;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionByCategoryService;
import com.insight.backend.service.category.FindCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private final DeleteQuestionService deleteQuestionService;
    private final FindQuestionByCategoryService findQuestionService;
    private final FindCategoryService findCategoryService;

    /**
     * Constructs a new QuestionController with the DeleteQuestionService and
     * FindQuestionsByCategoryService
     * 
     * @param deleteQuestionService the service to delete a question
     * 
     */
    @Autowired
    public QuestionController(DeleteQuestionService deleteQuestionService, FindQuestionByCategoryService findQuestionService, FindCategoryService findCategoryService) {
        this.deleteQuestionService = deleteQuestionService; 
        this.findQuestionService = findQuestionService;
        this.findCategoryService = findCategoryService;
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

    @GetMapping("/api/v1/categories/{categoryId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("categoryID") Long categoryID, @RequestParam(required = false, defaultValue = "asc") String sortDirection, @RequestParam(required = false, defaultValue = "id") String sortBy){
        Optional<Category> optionalCategory = findCategoryService.findCategoryById(categoryID);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            List<Question> questions = findQuestionService.findAllQuestions(category, sortDirection, sortBy);
            return ResponseEntity.ok(questions);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
