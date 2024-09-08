package com.project.bunnyCare.feedback.infrastructure;

import com.project.bunnyCare.feedback.domain.FeedbackEntity;
import com.project.bunnyCare.feedback.domain.FeedbackStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedbackStoreImpl implements FeedbackStore {

    private final FeedbackRepository feedbackRepository;

    @Override
    public void save(FeedbackEntity feedback) {
        feedbackRepository.save(feedback);
    }
}
