package com.insight.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.exception.CategoryNotFoundException;
import com.insight.backend.exception.QuestionNotFoundException;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.CreateQuestionService;
import com.insight.backend.service.question.DeleteQuestionService;
import com.insight.backend.service.question.FindQuestionService;

import jakarta.validation.Valid;

@RestController
@Validated // Aktiviert Validierung für Controller-Eingaben
public class QuestionController {

    private final DeleteQuestionService deleteQuestionService;
    private final FindQuestionService findQuestionService;
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
    public QuestionController(DeleteQuestionService deleteQuestionService, CreateQuestionService createQuestionService, 
                               FindQuestionService findQuestionService, FindCategoryService findCategoryService) {
        this.deleteQuestionService = deleteQuestionService;
        this.findQuestionService = findQuestionService;
        this.findCategoryService = findCategoryService;
        this.createQuestionService = createQuestionService;
    }

    /**
     * Handles DELETE requests for deleting a Question.
     *
     * @param questionID    the ID of the question to be deleted
     * @return a ResponseEntity containing a String confirming the deletion
     */
    @DeleteMapping("/api/v1/questions/{questionID}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionID") long questionID) {
        Question question = findQuestionService.findQuestionByID(questionID)
                .orElseThrow(() -> new QuestionNotFoundException(questionID));

        deleteQuestionService.deleteQuestion(question);
        return ResponseEntity.ok("Question successfully deleted");
    }

    @PostMapping("/api/v1/questions/new")
    public ResponseEntity<Object> postWithRequestBody(@Valid @RequestBody NewQuestionDTO newQuestionDTO) {
        QuestionResponseDTO responseDTO = this.createQuestionService.createQuestion(newQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/api/v1/categories/{categoryID}/questions")
    public ResponseEntity<List<Question>> getQuestionsByCategory(
            @PathVariable("categoryID") Long categoryID,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {

        Category category = findCategoryService.findCategoryById(categoryID)
                .filter(cat -> cat.getDeletedAt() == null) // Sicherstellen, dass die Kategorie nicht gelöscht ist
                .orElseThrow(() -> new CategoryNotFoundException(categoryID));

        List<Question> questions = findQuestionService.findQuestionsByCategory(category, sortDirection, sortBy);
        return ResponseEntity.ok(questions);
    }
}
