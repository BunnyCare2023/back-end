package com.project.bunnyCare.news.interfaces.dto;

import com.project.bunnyCare.news.domain.NewsEntity;

import java.time.LocalDateTime;

public record NewsDetailResponseDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy
) {

    public static NewsDetailResponseDto from(NewsEntity entity) {
        return new NewsDetailResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreateUser().getName()
        );
    }
}
