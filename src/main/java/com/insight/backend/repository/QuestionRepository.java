package com.insight.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.insight.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
