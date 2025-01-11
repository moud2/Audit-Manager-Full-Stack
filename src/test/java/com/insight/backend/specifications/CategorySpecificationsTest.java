package com.insight.backend.specifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.insight.backend.model.Category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Test class for CategorySpecifications.
 */
@ExtendWith(MockitoExtension.class)
public class CategorySpecificationsTest {

    /**
     * Test case for the Specification that checks if the category is not deleted.
     */
    @Test
    void testIsNotDeleted() {
        // Mock the CriteriaBuilder, CriteriaQuery, and Root objects
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Category> root = mock(Root.class);

        // Create the Specification for the "isNotDeleted" check
        Specification<Category> spec = CategorySpecifications.isNotDeleted();

        // Apply the specification to build the predicate
        spec.toPredicate(root, query, criteriaBuilder);

        // Verify that the criteriaBuilder's isNull method is called with "deletedAt"
        verify(criteriaBuilder).isNull(root.get("deletedAt"));
    }
}
