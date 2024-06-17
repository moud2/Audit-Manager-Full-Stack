package com.insight.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insight.backend.model.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}