package com.insight.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.repository.QuestionRepository;

@Service
public class FindQuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FindQuestionService(QuestionRepository questionRepository, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Saves a list of questions.
     *
     * @param questions the list of questions to save
     */
    public void saveQuestions(List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    /**
     * Saves a list of categories.
     *
     * @param categories the list of categories to save
     */
    public void saveCategories(List<Category> categories) {
        categoryRepository.saveAll(categories);
    }
}
