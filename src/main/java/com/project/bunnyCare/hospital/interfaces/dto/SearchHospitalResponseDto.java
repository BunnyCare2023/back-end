package com.project.bunnyCare.hospital.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public record SearchHospitalResponseDto(
        Long id,
        String hospitalName,
        String status,
        String dayOfWeek,
        @JsonFormat(pattern = "HH:mm")
        LocalTime openTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime closeTime,
        Integer distance,
        String address,
        List<String> services,
        String bookmarkState
) {
        public static SearchHospitalResponseDto of(HospitalResponse data){
                return new SearchHospitalResponseDto(
                        data.getId(),
                        data.getHospitalName(),
                        data.getWorkStatus(),
                        data.getDayOfWeek().getValue(),
                        data.getOpenTime(),
                        data.getCloseTime(),
                        data.getDistance(),
                        data.getAddress(),
                        data.getServices().stream().map(ServiceType::getValue).toList(),
                        data.getBookmarkState()
                );
        }


}
