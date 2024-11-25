package com.insight.backend.specifications;

import com.insight.backend.model.Question;
import com.insight.backend.model.Category;

import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecifications {
    
    /**
     * Specification to find a Question by its Category.
     *
     * @param category the Category of the Question
     * @return the specification to find the Question
     */
    public static Specification<Question> inCategory(Category category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
            .equal(root.get("category"), category);
    }

    public static Specification<Question> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder
            .isNull(root.get("DeletedAt"));
    }
}

