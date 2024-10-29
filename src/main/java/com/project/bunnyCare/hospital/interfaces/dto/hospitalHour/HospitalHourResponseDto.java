package com.project.bunnyCare.hospital.interfaces.dto.hospitalHour;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.domain.hospitalHour.HospitalHourEntity;

import java.time.LocalTime;

public record HospitalHourResponseDto(
        String dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime
) {

    public static HospitalHourResponseDto of(HospitalHourEntity entity) {
        return new HospitalHourResponseDto(entity.getDayOfWeek().getValue(), entity.getOpenTime(), entity.getCloseTime());
    }
}
