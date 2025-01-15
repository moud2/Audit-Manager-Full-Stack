package com.insight.backend.service.rating;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for generating CSV exports of categories and their associated questions.
 * Ensures that deleted categories or questions are excluded and that no duplicate entries are exported.
 */
@Service
public class CsvGeneratorService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Generates a CSV containing all categories and their associated questions.
     *
     * <p>Each row in the CSV follows the format: {@code "category","question"}.
     * Categories and questions marked as deleted are excluded, and duplicate entries are avoided.</p>
     *
     * @return A {@link ByteArrayInputStream} containing the CSV data.
     */
    public ByteArrayInputStream createCsv() {

        List<Category> categories = categoryRepository.findAll();


        StringBuilder csvContent = new StringBuilder();
        Set<String> exportedQuestions = new HashSet<>();

        for (Category category : categories) {
            if (category.getDeletedAt() != null) {
                continue;
            }

            for (Question question : category.getQuestions()) {
                if (question.getDeletedAt() != null) {
                    continue;
                }

                String categoryName = "\"" + category.getName() + "\"";
                String questionName = "\"" + question.getName().replace("\n", " ") + "\"";

                String questionKey = categoryName + "," + questionName;
                if (exportedQuestions.contains(questionKey)) {
                    continue;
                }

                exportedQuestions.add(questionKey);

                csvContent.append(categoryName)
                        .append(",")
                        .append(questionName)
                        .append("\n");
            }
        }

        return new ByteArrayInputStream(csvContent.toString().getBytes(StandardCharsets.UTF_8));
    }
}
