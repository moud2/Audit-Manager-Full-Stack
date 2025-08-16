package com.insight.backend.service.question;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.insight.backend.exception.InvalidQuestionException;
import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;

@Service
public class DeleteQuestionService {

    private final QuestionRepository questionRepository;

    public DeleteQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * sets the attribute deletedAt to the time of deletion
     *
     * @param question the question to be deleted
     */
    public void deleteQuestion(Question question) {
        if (question == null) {
            throw new InvalidQuestionException();
        }
        question.setDeletedAt(LocalDateTime.now());
        questionRepository.saveAndFlush(question);
    }
    

}
