package com.project.bunnyCare.hospital.interfaces.dto;

import com.project.bunnyCare.hospital.interfaces.dto.hospitalBreakTime.CreateHospitalBreakTimeRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.hospitalHour.CreateHospitalHourRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.hospitalService.CreateHospitalServiceRequestDto;

import java.util.List;

public record CreateHospitalRequestDto(
        String hospitalName,
        String city,
        String district,
        String address,
        String telNo,
        String note,
        Double latitude,
        Double longitude,
        String nightCare,
        String sundayCare,
        String holidayCare,
        String fullTimeCare,
        List<CreateHospitalServiceRequestDto> hospitalServices,
        List<CreateHospitalHourRequestDto> hospitalHours,
        List<CreateHospitalBreakTimeRequestDto> hospitalBreakTimes
) {
}
