package com.project.bunnyCare.hospital.interfaces.dto.hospitalBreakTime;

import com.project.bunnyCare.hospital.domain.hospitalBreakTime.HospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.WeekType;

import java.time.LocalTime;

public record HospitalBreakTimeResponseDto(
        String weekType,
        LocalTime startTime,
        LocalTime endTime
) {

    public static HospitalBreakTimeResponseDto of(HospitalBreakTimeEntity entity) {
        return new HospitalBreakTimeResponseDto(
                entity.getWeekType().getValue(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
