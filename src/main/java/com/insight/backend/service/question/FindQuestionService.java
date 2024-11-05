package com.insight.backend.service.question;


import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FindQuestionService {
    
    private final QuestionRepository questionRepository;

    public FindQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> findQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
    }

    /**
     * returns all questions
     * @return list of all questions
     */
    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }
}
