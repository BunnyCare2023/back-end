package com.project.bunnyCare.feedback.interfaces.dto;

import com.project.bunnyCare.feedback.domain.FeedbackType;
import com.project.bunnyCare.profileCard.interfaces.dto.AppearanceResponseDto;
import com.project.bunnyCare.profileCard.interfaces.dto.ImageDetailResponseDto;
import com.project.bunnyCare.user.interfaces.dto.UserDetailResponseDto;

import java.util.List;

public record FeedbackDetailResponseDto(
        Long id,
        FeedbackType type,
        String title,
        String content,
        String hospitalName,
        String hospitalAddress,
        Double longitude,
        Double latitude,
        List<ImageDetailResponseDto> images,
        UserDetailResponseDto createdUser

) {
}
