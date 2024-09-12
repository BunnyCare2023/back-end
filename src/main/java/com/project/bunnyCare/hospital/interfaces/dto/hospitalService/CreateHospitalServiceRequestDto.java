package com.project.bunnyCare.hospital.interfaces.dto.hospitalService;

import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;

public record CreateHospitalServiceRequestDto(
        ServiceType serviceName,
        String serviceStatus
) {
}
