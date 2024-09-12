package com.project.bunnyCare.hospital.interfaces.dto.hospitalHour;

import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;

import java.sql.Time;

public record CreateHospitalHourRequestDto(
        DayOfWeek dayOfWeek,
        Time openTime,
        Time closeTime
) {
}
