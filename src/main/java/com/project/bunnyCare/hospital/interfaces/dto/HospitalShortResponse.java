package com.project.bunnyCare.hospital.interfaces.dto;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HospitalShortResponse {
    private Long id;
    private String hospitalName;
    private String address;
    private List<String> services;


    public static HospitalShortResponse of(HospitalEntity entity) {
        List<String> services = entity.getHospitalServices().stream().map(HospitalServiceEntity::getServiceName).map(ServiceType::getValue).toList();
        return new HospitalShortResponse(entity.getId(), entity.getHospitalName(), entity.getAddress(), services);
    }
}
