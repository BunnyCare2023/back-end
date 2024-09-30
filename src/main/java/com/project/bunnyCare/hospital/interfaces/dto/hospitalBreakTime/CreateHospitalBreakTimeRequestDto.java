package com.project.bunnyCare.hospital.interfaces.dto.hospitalBreakTime;

import com.project.bunnyCare.hospital.domain.hospitalBreakTime.WeekType;

import java.time.LocalTime;

public record CreateHospitalBreakTimeRequestDto(
        WeekType weekType,
        LocalTime startTime,
        LocalTime endTime,
        Integer order
) {
}
