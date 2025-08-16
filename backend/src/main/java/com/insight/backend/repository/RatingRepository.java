package com.insight.backend.repository;

import com.insight.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Finds all ratings associated with the specified audit ID.
     *
     * @param auditId The ID of the audit.
     * @return A list of ratings for the given audit ID.
     */
    List<Rating> findByAuditId(Long auditId);

}
