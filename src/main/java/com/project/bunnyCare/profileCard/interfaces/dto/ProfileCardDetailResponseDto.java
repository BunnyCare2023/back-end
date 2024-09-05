package com.project.bunnyCare.profileCard.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;

import java.time.LocalDate;
import java.util.List;

public record ProfileCardDetailResponseDto(
        Long id,
        String rabbitName,
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDate birthDate,
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDate adoptionDate,
        Character sex,
        ImageDetailResponseDto profileImage,
        List<AppearanceResponseDto> appearances
) {

    public static ProfileCardDetailResponseDto from(ProfileCardEntity entity){
        return new ProfileCardDetailResponseDto(
                entity.getId(),
                entity.getRabbitName(),
                entity.getBirthDate(),
                entity.getAdoptionDate(),
                entity.getSex(),
                ImageDetailResponseDto.from(entity.getProfileImage()),
                entity.getAppearances().stream().map(AppearanceResponseDto::from).toList()
        );
    }
}
