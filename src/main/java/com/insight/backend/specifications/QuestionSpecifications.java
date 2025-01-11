package com.insight.backend.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;

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

    public static Specification<Question> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    /**
     * Specification to find questions that are not deleted.
     * root: Accesses fields of the "Question" entity.
     * query: Represents the criteria query (unused here).
     * criteriaBuilder: Builds the condition to check if "deletedAt" is null.
     * @return a Specification to find questions where deletedAt is null.
     */
    public static Specification<Question> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder
            .isNull(root.get("deletedAt"));
    }
}

