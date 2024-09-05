package com.project.bunnyCare.profileCard.interfaces.dto;

import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceType;
import com.project.bunnyCare.user.domain.UserEntity;

import java.time.LocalDate;
import java.util.List;

public record CreateProfileCardRequestDto(
        String rabbitName,
        LocalDate birthDate,
        LocalDate adoptionDate,
        Character sex,
        List<AppearanceType> appearanceTypes
) {

    public ProfileCardEntity toEntity(ImageEntity image, List<AppearanceEntity> appearanceTypes, UserEntity user) {
        return new ProfileCardEntity(
                rabbitName,
                birthDate,
                adoptionDate,
                sex,
                image,
                appearanceTypes,
                user,
                'N');
    }
}
