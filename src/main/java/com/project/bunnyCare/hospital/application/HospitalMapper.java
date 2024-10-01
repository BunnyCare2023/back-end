package com.project.bunnyCare.hospital.application;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.HospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.HospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.interfaces.dto.CreateHospitalRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface HospitalMapper {

    default HospitalEntity toEntity(CreateHospitalRequestDto dto){
        HospitalEntity hospital = HospitalEntity.builder()
                .hospitalName(dto.hospitalName())
                .city(dto.city())
                .district(dto.district())
                .address(dto.address())
                .telNo(dto.telNo())
                .note(dto.note())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .nightCare(dto.nightCare())
                .sundayCare(dto.sundayCare())
                .holidayCare(dto.holidayCare())
                .fullTimeCare(dto.fullTimeCare())
                .deleteYn("N")
                .build();

        List<HospitalServiceEntity> hospitalServices = dto.hospitalServices()
                .stream().map(serviceDto -> HospitalServiceEntity.builder()
                        .serviceName(serviceDto.serviceName())
                        .serviceStatus(serviceDto.serviceStatus())
                        .hospital(hospital)
                        .build())
                .toList();

        List<HospitalHourEntity> hospitalHours = dto.hospitalHours()
                .stream().map(hourDto -> HospitalHourEntity.builder()
                        .dayOfWeek(hourDto.dayOfWeek())
                        .openTime(hourDto.openTime())
                        .closeTime(hourDto.closeTime())
                        .orderNo(hourDto.order())
                        .hospital(hospital)
                        .build())
                .toList();

        List<HospitalBreakTimeEntity> hospitalBreakTimes = dto.hospitalBreakTimes()
                .stream().map(breakTimeDto -> HospitalBreakTimeEntity.builder()
                        .weekType(breakTimeDto.weekType())
                        .hospital(hospital)
                        .startTime(breakTimeDto.startTime())
                        .endTime(breakTimeDto.endTime())
                        .build())
                .toList();

        hospital.update(hospitalServices, hospitalHours, hospitalBreakTimes);
        return hospital;
    }

}
