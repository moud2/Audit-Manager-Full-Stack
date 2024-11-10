package com.insight.backend.specifications;

import com.insight.backend.model.Question;
import com.insight.backend.model.Category;

import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecifications {
    
    public static Specification<Question> inCategory(Category category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

}
