package com.project.bunnyCare.profileCard.interfaces.dto;

import com.project.bunnyCare.profileCard.domain.appearance.AppearanceEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceType;

public record AppearanceResponseDto(
        Long id,
        AppearanceType type
) {

    public static AppearanceResponseDto from(AppearanceEntity entity){
        return new AppearanceResponseDto(
                entity.getId(),
                entity.getType()
        );
    }
}
