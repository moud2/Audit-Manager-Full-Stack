package com.insight.backend.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.insight.backend.model.Audit;
import org.junit.jupiter.api.Test;

import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuditSpecificationsTest {

    @Test
    void hasName() {
        Specification<Audit> spec = AuditSpecifications.hasName("TestAudit");
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Audit> root = mock(Root.class);

        spec.toPredicate(root, query, cb);

        verify(root).get("name");
        verify(cb).equal(any(), eq("TestAudit"));
    }

    @Test
    void hasCustomer() {
        Specification<Audit> spec = AuditSpecifications.hasCustomer("TestCustomer");
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Audit> root = mock(Root.class);

        spec.toPredicate(root, query, cb);

        verify(root).get("customer");
        verify(cb).equal(any(), eq("TestCustomer"));
    }

    @Test
    void customerStartsWith() {
        Specification<Audit> spec = AuditSpecifications.customerStartsWith("Test");
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Audit> root = mock(Root.class);

        spec.toPredicate(root, query, cb);

        verify(root).get("customer");
        verify(cb).like(any(), eq("Test%"));
    }

    @Test
    void customerContains() {
        Specification<Audit> spec = AuditSpecifications.customerContains("Test");
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Audit> root = mock(Root.class);

        spec.toPredicate(root, query, cb);

        verify(root).get("customer");
        verify(cb).like(any(), eq("%Test%"));
    } 

    @Test
    void isNotDeleted() {
    Specification<Audit> spec = AuditSpecifications.isNotDeleted();

    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    CriteriaQuery<?> query = mock(CriteriaQuery.class);
    Root<Audit> root = mock(Root.class);

    spec.toPredicate(root, query, cb);

    verify(root).get("deletedAt"); 
    verify(cb).isNull(any()); 
}

}