package com.project.bunnyCare.profileCard.interfaces.dto;

import com.project.bunnyCare.image.domain.ImageEntity;

public record ImageDetailResponseDto(
        Long id,
        String originalName,
        String storedName
) {

    public static ImageDetailResponseDto from(ImageEntity entity){
        return new ImageDetailResponseDto(
                entity.getId(),
                entity.getOriginalName(),
                entity.getStoredName()
        );
    }
}
