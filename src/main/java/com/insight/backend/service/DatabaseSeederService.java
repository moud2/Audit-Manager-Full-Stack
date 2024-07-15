package com.insight.backend.service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import com.opencsv.CSVReader;

import org.springframework.stereotype.Service;

@Service
public class DatabaseSeederService {

    private final SaveCategoryService saveCategoryService;
    private final SaveQuestionService saveQuestionService;

    public DatabaseSeederService(SaveCategoryService saveCategoryService, SaveQuestionService saveQuestionService) {
        this.saveCategoryService = saveCategoryService;
        this.saveQuestionService = saveQuestionService;
    }

    public void seedDatabaseFromFiles() throws Exception {
        System.out.println("Seeding database from files...");

        Path categoryPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-categories.csv").toURI());

        List<String[]> categories = readAllLinesFromCsv(categoryPath);

        Map<Integer, Category> categoryMap = new java.util.HashMap<>(Map.of());

        // Ausgabe der eingelesenen Daten
        for (String[] entry : categories) {
            System.out.println(entry[0] + ", Value: " + entry[1]);
            Category category = new Category();
            category.setName(entry[1]);
            categoryMap.put(Integer.parseInt(entry[0]), saveCategoryService.saveCategory(category));
        }

        Path questionPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-questions.csv").toURI());

        List<String[]> questions = readAllLinesFromCsv(questionPath);

        for (String[] entry : questions) {
            System.out.println("ID: " + entry[0] + ", Value: " + entry[1]);
            Question question = new Question();
            question.setName(entry[1]);
            question.setCategory(categoryMap.get(Integer.parseInt(entry[0])));
            saveQuestionService.saveQuestion(question);
        }
    }

    private static List<String[]> readAllLinesFromCsv(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
