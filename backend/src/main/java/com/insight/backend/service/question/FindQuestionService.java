package com.insight.backend.service.question;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.specifications.QuestionSpecifications;

@Service
public class FindQuestionService {
    
    private final QuestionRepository questionRepository;

    public FindQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional <Question> findQuestionByID(Long id) {
        return questionRepository.findById(id);
    }

    /**
     * Finds all Questions of the specified Category.
     *
     * @param name the name of the Category to search for
     * @param sortDirection the direction to sort the results
     * @return a list of all Questions of the specified Category
     */
    public List<Question> findQuestionsByName(String name, String sortDirection, String sortBy) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

        return questionRepository.findAll(QuestionSpecifications.hasName(name).and(QuestionSpecifications.isNotDeleted()), sort);
    }

    /**
     * Finds all Questions of the specified Category.
     *
     * @param category the name of the Category to search for
     * @param sortDirection the direction to sort the results
     * @param sortBy the attribute by which to sort the questions
     * @return a list of all Questions of the specified Category
     */
    public List<Question> findQuestionsByCategory(Category category, String sortDirection, String sortBy) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

        return questionRepository.findAll(QuestionSpecifications.inCategory(category)
        .and(QuestionSpecifications.isNotDeleted()), sort);
    }


}
