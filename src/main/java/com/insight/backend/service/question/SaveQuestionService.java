package com.insight.backend.service.question;

import com.insight.backend.repository.QuestionRepository;
import com.insight.backend.model.Question;
import org.springframework.stereotype.Service;

@Service
public class SaveQuestionService {
    private final QuestionRepository questionRepository;

    public SaveQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Saves a given question to the db
     *
     * @param question the question to be saved
     * @return the saved question, or null if the input question is null
     */
    public Question saveQuestion(Question question) {
        if (question == null) return null;
        return questionRepository.saveAndFlush(question);
    }
}
