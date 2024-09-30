package com.project.bunnyCare.hospital.interfaces.dto.hospitalHour;

import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;

import java.time.LocalTime;

public record CreateHospitalHourRequestDto(
        DayOfWeek dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        Integer order
) {
}
