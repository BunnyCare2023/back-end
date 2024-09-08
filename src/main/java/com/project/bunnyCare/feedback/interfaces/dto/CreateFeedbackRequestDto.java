package com.project.bunnyCare.feedback.interfaces.dto;

import com.project.bunnyCare.feedback.domain.FeedbackType;

public record CreateFeedbackRequestDto(
        FeedbackType type,
        String title,
        String content,
        String hospitalName,
        String hospitalAddress,
        Double longitude,
        Double latitude
) {
}
