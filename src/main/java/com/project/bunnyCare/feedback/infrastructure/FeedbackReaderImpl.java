package com.project.bunnyCare.feedback.infrastructure;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.feedback.domain.FeedbackEntity;
import com.project.bunnyCare.feedback.domain.FeedbackReader;
import com.project.bunnyCare.feedback.domain.FeedbackResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedbackReaderImpl implements FeedbackReader {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackEntity> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public FeedbackEntity findById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ApiException(FeedbackResponseCode.READ_DETAIL_FAIL));
    }
}
