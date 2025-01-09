package com.insight.backend.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import org.junit.jupiter.api.Test;

import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class QuestionSpecificationTest {
        
    @Test
    void inCategory() {
        Category category = new Category();
        Specification<Question> spec = QuestionSpecifications.inCategory(category);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Question> root = mock(Root.class);

        spec.toPredicate(root, query, cb);

        verify(root).get("category");
        verify(cb).equal(any(), eq(category));
    }

    @Test
    void isNotDeleted() {
        Specification<Question> spec = QuestionSpecifications.isNotDeleted();
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Question> root = mock(Root.class);

        spec.toPredicate(root, query, cb);
        
        verify(cb).isNull(root.get("DeletedAt"));
    }
}
