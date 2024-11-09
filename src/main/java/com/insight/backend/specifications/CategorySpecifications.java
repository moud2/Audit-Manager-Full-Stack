package com.insight.backend.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.insight.backend.model.Category;

/**
 * Specifications for querying Category entities.
 */
public class CategorySpecifications {

    /**
     * Specification to find a Category by its ID.
     *
     * @param id the ID of the Category to find.
     * @return the specification to find the Category.
     */
    public static Specification<Category> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    /**
     * Specification to find categories that are not deleted.
     *
     * @return the specification to filter non-deleted categories.
     */
    public static Specification<Category> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }
}