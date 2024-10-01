package com.project.bunnyCare.hospital.interfaces.dto;

import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;

import java.util.List;

public record SearchHospitalRequestDto(
    String city,
    String district,
    double latitude,
    double longitude,
    String hospitalStatus,
    List<ServiceType> services,
    String nightCareYn,
    String sundayCareYn,
    String holidayCareYn,
    String fullTimeCareYn,
    Integer currentPage,
    Integer pageSize,
    String sortType
) {
}
