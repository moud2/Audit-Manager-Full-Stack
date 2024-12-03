package com.insight.backend.specifications;

import com.insight.backend.model.Audit;

import org.springframework.data.jpa.domain.Specification;

public class AuditSpecifications {
    /**
     * Specification to find an Audit by its name.
     *
     * @param name the exact name of the Audit
     * @return the specification to find the Audit
     */
    public static Specification<Audit> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    /**
     * Specification to find an Audit by its customer.
     *
     * @param customer the exact name of the customer
     * @return the specification to find the Audit
     */
    public static Specification<Audit> hasCustomer(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer"), customer);
    }

    /**
     * Specification to find an Audit by its customer.
     *
     * @param customer the start of the name of the customer
     * @return the specification to find the Audit
     */
    public static Specification<Audit> customerStartsWith(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customer"), customer + "%");
    }

    /**
     * Specification to find an Audit by its customer.
     *
     * @param customer the name of the customer
     * @return the specification to find the Audit
     */
    public static Specification<Audit> customerContains(String customer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customer"), "%" + customer + "%");
    }

    public static Specification<Audit> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

}
