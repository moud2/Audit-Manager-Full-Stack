package com.insight.backend.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.insight.backend.model.Category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CategorySpecifications {

    /**
     * Creates a specification to find categories that are not deleted.
     * root: Accesses fields of the "Category" entity.
     * query: Represents the criteria query (unused here).
     * criteriaBuilder: Builds the condition to check if "deletedAt" is null.
     * @return a Specification to find categories where deletedAt is null.
     */
    public static Specification<Category> isNotDeleted() {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }
}
