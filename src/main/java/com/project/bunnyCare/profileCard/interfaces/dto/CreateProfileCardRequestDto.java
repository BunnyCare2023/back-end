package com.project.bunnyCare.profileCard.interfaces.dto;

import com.project.bunnyCare.profileCard.domain.appearance.AppearanceType;

import java.time.LocalDate;
import java.util.List;

public record CreateProfileCardRequestDto(
        String rabbitName,
        LocalDate birthDate,
        LocalDate adoptionDate,
        Character sex,
        List<AppearanceType> appearanceTypes
) {
}
