package com.insight.backend.service.question;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insight.backend.dto.NewQuestionDTO;
import com.insight.backend.dto.QuestionResponseDTO;
import com.insight.backend.exception.CategoryNotFoundException;
import com.insight.backend.exception.QuestionAlreadyExistsException;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;

/**
 * Service class for creating questions.
 */
@Service
public class CreateQuestionService {

    private final FindCategoryService findCategoryService;
    private final SaveQuestionService saveQuestionService;
    private final FindQuestionService findQuestionService;

    /**
     * Konstruktor für den CreateQuestionService.
     *
     * @param findCategoryService der Service zum Finden von Kategorien
     * @param saveQuestionService der Service zum Speichern von Fragen
     * @param findQuestionService der Service zum Finden von Fragen
     */
    public CreateQuestionService(FindCategoryService findCategoryService, SaveQuestionService saveQuestionService, FindQuestionService findQuestionService) {
        this.findCategoryService = findCategoryService;
        this.saveQuestionService = saveQuestionService;
        this.findQuestionService = findQuestionService;
    }

    /**
     * Erstellt eine neue Frage basierend auf den bereitgestellten Daten.
     *
     * @param newQuestionDTO das DTO mit den Details der neuen Frage
     * @return ein QuestionResponseDTO mit den Details der erstellten Frage
     * @throws CategoryNotFoundException, wenn die Kategorie-ID ungültig ist
     * @throws QuestionAlreadyExistsException, wenn eine Frage mit demselben Namen existiert
     */
    public QuestionResponseDTO createQuestion(NewQuestionDTO newQuestionDTO) {
        // Frage-Objekt initialisieren
        Question question = new Question();
        question.setName(newQuestionDTO.getName());
    
        // Überprüfen, ob der Frage-Name bereits existiert
        List<Question> existingQuestions = this.findQuestionService
                .findQuestionsByName(newQuestionDTO.getName(), "desc", "name");
    
        if (!existingQuestions.isEmpty()) {
            throw new QuestionAlreadyExistsException("Question already exists.");
        }
    
        // Kategorie anhand der ID abrufen
        Category category = this.findCategoryService
                .findCategoryById(newQuestionDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(newQuestionDTO.getCategoryId()));
    
        question.setCategory(category);

        // Frage speichern
        Question savedQuestion = this.saveQuestionService.saveQuestion(question);
    
        // Antwort-DTO erstellen
        QuestionResponseDTO responseDTO = new QuestionResponseDTO();
        responseDTO.setId(savedQuestion.getId());
        responseDTO.setName(savedQuestion.getName());
        responseDTO.setCategory(savedQuestion.getCategory());
    
        return responseDTO;
    }
}
