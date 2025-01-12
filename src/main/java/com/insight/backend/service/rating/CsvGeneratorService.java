package com.insight.backend.service.rating;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CsvGeneratorService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ByteArrayInputStream createCsv() {
        // Retrieve all Categories
        List<Category> categories = categoryRepository.findAll();

        // Create CSV Content
        StringBuilder csvContent = new StringBuilder();
        Set<String> exportedQuestions = new HashSet<>(); // Track exported questions to avoid duplicates

        for (Category category : categories) {
            // Skip categories that are marked as deleted
            if (category.getDeletedAt() != null) {
                continue;
            }

            for (Question question : category.getQuestions()) {
                // Skip questions that are marked as deleted
                if (question.getDeletedAt() != null) {
                    continue;
                }

                // Replace commas with semicolons and remove newlines
                String categoryName = category.getName().replace(",", ";");
                String questionName = question.getName().replace(",", ";").replace("\n", " ");

                // Avoid exporting duplicate questions
                String questionKey = categoryName + "," + questionName;
                if (exportedQuestions.contains(questionKey)) {
                    continue;
                }

                exportedQuestions.add(questionKey);

                // Format: category,question
                csvContent.append(categoryName)
                        .append(",")
                        .append(questionName)
                        .append("\n");
            }
        }

        // Convert StringBuilder to ByteArrayInputStream
        return new ByteArrayInputStream(csvContent.toString().getBytes(StandardCharsets.UTF_8));
    }
}
