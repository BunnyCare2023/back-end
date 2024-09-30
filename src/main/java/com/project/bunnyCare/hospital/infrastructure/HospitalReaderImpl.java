package com.project.bunnyCare.hospital.infrastructure;

import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.hospital.domain.QHospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.domain.hospitalHour.QHospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.QHospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;
import com.project.bunnyCare.hospital.interfaces.dto.HospitalResponse;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HospitalReaderImpl implements HospitalReader {

    private final HospitalRepository hospitalRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HospitalResponse> findHospitalsForSearch(SearchHospitalRequestDto dto) {
        QHospitalEntity hospital = QHospitalEntity.hospitalEntity;
        QHospitalHourEntity hospitalHour = QHospitalHourEntity.hospitalHourEntity;
        QHospitalServiceEntity hospitalService = QHospitalServiceEntity.hospitalServiceEntity;

        LocalDate today = LocalDate.now();
        // 오늘 요일 추출
        DayOfWeek day = DayOfWeek.fromJavaDayOfWeek(today.getDayOfWeek());

        // 거리 계산 식
        NumberExpression<Integer> distanceExpression = Expressions.numberTemplate(Integer.class,
                "round(6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1}))))",
                dto.latitude(), hospital.latitude, hospital.longitude, dto.longitude());

        // 현재 시간
        TimeTemplate<LocalTime> now = Expressions.timeTemplate(LocalTime.class, "now()");

        // 진료 상태 계산
        StringExpression workStatusExpression = new CaseBuilder()
                .when(now.between(hospitalHour.openTime, hospitalHour.closeTime))
                .then("진료중")
                .otherwise("진료 종료");

        // 검색 조건 빌더
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // 시 조건
        booleanBuilder.and(hospital.city.eq(dto.city()));
        // 진료 상태 조건
        if(dto.hospitalStatus() != null && dto.hospitalStatus().equals("진료중")) booleanBuilder.and(workStatusExpression.eq("진료중"));

        // 서비스 조건
        dto.services().forEach(serviceType -> {
            if(serviceType.equals(ServiceType.MALE_NEUTERING)){
                booleanBuilder.and(hospitalService.serviceName.eq(ServiceType.NEUTERING).and(hospitalService.serviceStatus.eq("M")));
            }else if(serviceType.equals(ServiceType.FEMAILE_NEUTERING)){
                booleanBuilder.and(hospitalService.serviceName.eq(ServiceType.NEUTERING).and(hospitalService.serviceStatus.eq("F")));
            }else {
                booleanBuilder.and(hospitalService.serviceName.eq(serviceType)).and(hospitalService.serviceStatus.eq("Y"));
            }
        });
        // 야간진료, 일요일진료, 공휴일진료, 풀타임진료 조건
        if(dto.nightCareYn() != null && dto.nightCareYn().equals("Y")) booleanBuilder.and(hospital.nightCare.eq("Y"));
        if(dto.sundayCareYn() != null && dto.sundayCareYn().equals("Y")) booleanBuilder.and(hospital.sundayCare.eq("Y"));
        if(dto.holidayCareYn() != null && dto.holidayCareYn().equals("Y")) booleanBuilder.and(hospital.holidayCare.eq("Y"));
        if(dto.fullTimeCareYn() != null && dto.fullTimeCareYn().equals("Y")) booleanBuilder.and(hospital.fullTimeCare.eq("Y"));



        List<HospitalResponse> hospitalResult = jpaQueryFactory
                .selectDistinct(Projections.constructor(HospitalResponse.class,
                        hospital.id,
                        hospital.hospitalName,
                        workStatusExpression.as("workStatus"),
                        hospitalHour.dayOfWeek,
                        hospitalHour.openTime,
                        hospitalHour.closeTime,
                        distanceExpression.as("distance"),
                        hospital.address
                ))
                .from(hospital)
                .leftJoin(hospitalHour).on(hospital.eq(hospitalHour.hospital).and(hospitalHour.dayOfWeek.eq(day)))
                .leftJoin(hospitalService).on(hospital.eq(hospitalService.hospital))
                .where(booleanBuilder)
                .fetch();

        List<Long> hospitalIds = hospitalResult.stream().map(HospitalResponse::getId).toList();
        List<HospitalServiceEntity> services = jpaQueryFactory
                .select(hospitalService)
                .from(hospitalService)
                .where(hospitalService.hospital.id.in(hospitalIds))
                .fetch();

        Map<Long, List<ServiceType>> servicesMap = services.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.getHospital().getId(),
                        Collectors.mapping(HospitalServiceEntity::getServiceName, Collectors.toList())
                ));

        hospitalResult.forEach(hospitalResponse -> hospitalResponse.setServices(servicesMap.get(hospitalResponse.getId())));


        return hospitalResult;
    }
}
