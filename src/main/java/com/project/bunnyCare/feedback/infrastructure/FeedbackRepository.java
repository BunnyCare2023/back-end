package com.project.bunnyCare.feedback.infrastructure;

import com.project.bunnyCare.feedback.domain.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
}
