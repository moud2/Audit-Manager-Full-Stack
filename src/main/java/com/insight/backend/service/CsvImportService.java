package com.insight.backend.service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvImportService {

    private final SaveCategoryService saveCategoryService;
    private final SaveQuestionService saveQuestionService;

    public CsvImportService(SaveCategoryService saveCategoryService, SaveQuestionService saveQuestionService) {
        this.saveCategoryService = saveCategoryService;
        this.saveQuestionService = saveQuestionService;
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

            Map<String, Category> categoryMap = new HashMap<>();

            for (String[] row : rows) {
                if (row.length < 2) {
                    throw new IllegalArgumentException("Ungültige CSV-Zeile: " + String.join(",", row));
                }

                String categoryName = row[0];
                String questionContent = row[1];

                Category category = categoryMap.computeIfAbsent(categoryName, name -> {
                    Category newCategory = new Category();
                    newCategory.setName(name);
                    return saveCategoryService.saveCategory(newCategory);
                });

                Question question = new Question();
                question.setName(questionContent);
                question.setCategory(category);
                saveQuestionService.saveQuestion(question);
            }
        }
    }
}