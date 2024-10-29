package com.project.bunnyCare.hospital.interfaces.dto.hospitalService;

import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;

public record SearchHospitalServiceResponseDto(
        String serviceName,
        String serviceStatus
) {

    public static SearchHospitalServiceResponseDto of(HospitalServiceEntity entity) {
        return new SearchHospitalServiceResponseDto(entity.getServiceName().getValue(), entity.getServiceStatus());
    }
}
