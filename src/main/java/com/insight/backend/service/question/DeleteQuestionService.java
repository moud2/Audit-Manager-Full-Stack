package com.insight.backend.service.question;

import com.insight.backend.model.Question;
import com.insight.backend.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DeleteQuestionService {

    private final QuestionRepository questionRepository;

    public DeleteQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * sets the attribute deletedAt to the time of deletion
     *
     * @param id of question to be deleted
     */
    public void deleteQuestion(Long id){
        Question question = questionRepository.findById(id).get();
        question.setDeletedAt(LocalDateTime.now());
    }

}
