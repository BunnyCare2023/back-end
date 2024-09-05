package com.project.bunnyCare.profileCard.interfaces.dto;

import java.time.LocalDate;

public record UpdateProfileCardRequestDto(
        Long id,
        String rabbitName,
        LocalDate birthDate,
        LocalDate adoptionDate,
        Character sex
) {
}
