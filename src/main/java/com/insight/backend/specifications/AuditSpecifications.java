package com.insight.backend.specifications;

import com.insight.backend.model.Audit;

import org.springframework.data.jpa.domain.Specification;

public class AuditSpecifications {
    public static Specification<Audit> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Audit> hasCustomer(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer"), customer);
    }

    // customer starts with
    public static Specification<Audit> customerStartsWith(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customer"), customer + "%");
    }

    // customer contains
    public static Specification<Audit> customerContains(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customer"), "%" + customer + "%");
    }
//    public static Specification<Audit> hasCategory(Long categoryId) {
//        return (root, query, criteriaBuilder) -> {
//            Join<Audit, Category> join = root.join("categories");
//            return criteriaBuilder.equal(join.get("id"), categoryId);
//        };
//    }
}
