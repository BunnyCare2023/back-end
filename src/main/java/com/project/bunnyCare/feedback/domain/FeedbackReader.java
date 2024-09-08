package com.project.bunnyCare.feedback.domain;

import java.util.List;

public interface FeedbackReader {

    List<FeedbackEntity> findAll();
    FeedbackEntity findById(Long id);
}
