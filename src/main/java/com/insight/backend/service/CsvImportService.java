package com.insight.backend.service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CsvImportService {

    private final SaveCategoryService saveCategoryService;
    private final SaveQuestionService saveQuestionService;
    private final FindCategoryService findCategoryService;

    public CsvImportService(SaveCategoryService saveCategoryService, SaveQuestionService saveQuestionService, FindCategoryService findCategoryService) {
        this.saveCategoryService = saveCategoryService;
        this.saveQuestionService = saveQuestionService;
        this.findCategoryService = findCategoryService;
    }

    /**
     * Imports categories and questions from a CSV file.
     *
     * @param file the CSV file to import
     * @throws Exception if an error occurs while processing the file
     */
    public void importCsv(MultipartFile file) throws Exception {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = csvReader.readAll();

            // Kategorie-Cache, um Doppeleinfügungen zu vermeiden
            Map<String, Category> categoryMap = new HashMap<>();

            for (String[] row : rows) {
                if (row.length < 2) {
                    throw new IllegalArgumentException("Ungültige CSV-Zeile: " + String.join(",", row));
                }

                String categoryName = row[0];
                String questionContent = row[1];

                // Zuerst prüfen, ob die Kategorie bereits existiert
                Category category = categoryMap.computeIfAbsent(categoryName, name -> {
                    Optional<Category> existingCategory = findCategoryService.findCategoryByName(name);
                    return existingCategory.orElseGet(() -> {
                        // Wenn die Kategorie nicht existiert, erstellen und speichern
                        Category newCategory = new Category();
                        newCategory.setName(name);
                        return saveCategoryService.saveCategory(newCategory);
                    });
                });

                // Prüfen, ob die Frage bereits in der Kategorie existiert
                boolean questionExists = category.getQuestions().stream()
                        .anyMatch(q -> q.getName().equalsIgnoreCase(questionContent));
                if (questionExists) {
                    // Überspringe die Frage, wenn sie bereits existiert
                    continue;
                }

                // Erstelle eine neue Frage und füge sie der Kategorie hinzu
                Question question = new Question();
                question.setName(questionContent);
                question.setCategory(category);
                saveQuestionService.saveQuestion(question);
            }
        }
    }

}