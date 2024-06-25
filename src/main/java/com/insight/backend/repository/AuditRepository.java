package com.insight.backend.repository;

import com.insight.backend.model.Audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}