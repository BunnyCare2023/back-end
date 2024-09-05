package com.project.bunnyCare.profileCard.interfaces.dto;

import com.project.bunnyCare.image.domain.ImageEntity;

public record ImageDetailResponseDto(
        Long id,
        String originalName,
        String storedPath
) {

    public static ImageDetailResponseDto from(ImageEntity entity){
        return new ImageDetailResponseDto(
                entity.getId(),
                entity.getOriginalName(),
                entity.getStoredPath()
        );
    }
}
