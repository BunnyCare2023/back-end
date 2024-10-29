package com.project.bunnyCare.hospital.infrastructure;

import com.project.bunnyCare.bookmark.domain.QBookmarkEntity;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.hospital.domain.QHospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.QHospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.domain.hospitalHour.QHospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.QHospitalServiceEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;
import com.project.bunnyCare.hospital.interfaces.dto.HospitalResponse;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HospitalReaderImpl implements HospitalReader {

    private final HospitalRepository hospitalRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<HospitalResponse> findHospitalsForSearch(SearchHospitalRequestDto dto, Pageable pageable) {
        Long userId = AuthUtil.getUserId();
        QHospitalEntity hospital = QHospitalEntity.hospitalEntity;
        QHospitalHourEntity hospitalHour = QHospitalHourEntity.hospitalHourEntity;
        QHospitalServiceEntity hospitalService = QHospitalServiceEntity.hospitalServiceEntity;
        QBookmarkEntity bookmark = QBookmarkEntity.bookmarkEntity;

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
        // 삭제 조건
        booleanBuilder.and(hospital.deleteYn.eq("N"));
        // 진료 상태 조건
        if(dto.hospitalStatus() != null && dto.hospitalStatus().equals("진료중")) booleanBuilder.and(workStatusExpression.eq("진료중"));

        // 서비스 조건
        if(dto.services() != null && !dto.services().isEmpty()) {
            dto.services().forEach(serviceType -> {
                BooleanBuilder serviceCondition = new BooleanBuilder();
                if (serviceType.equals(ServiceType.MALE_NEUTERING)) {
                    serviceCondition.and(hospitalService.serviceName.eq(ServiceType.NEUTERING)
                            .and(hospitalService.serviceStatus.in("M", "Y")));
                } else if (serviceType.equals(ServiceType.FEMAILE_NEUTERING)) {
                    serviceCondition.and(hospitalService.serviceName.eq(ServiceType.NEUTERING)
                            .and(hospitalService.serviceStatus.in("F", "Y")));
                } else {
                    serviceCondition.and(hospitalService.serviceName.eq(serviceType)
                            .and(hospitalService.serviceStatus.eq("Y")));
                }

                booleanBuilder.and(JPAExpressions
                        .selectOne()
                        .from(hospitalService)
                        .where(hospitalService.hospital.eq(hospital)
                                .and(serviceCondition))
                        .exists());
            });
        }

        // 야간진료, 일요일진료, 공휴일진료, 풀타임진료 조건
        if(dto.nightCareYn() != null && dto.nightCareYn().equals("Y")) booleanBuilder.and(hospital.nightCare.eq("Y"));
        if(dto.sundayCareYn() != null && dto.sundayCareYn().equals("Y")) booleanBuilder.and(hospital.sundayCare.eq("Y"));
        if(dto.holidayCareYn() != null && dto.holidayCareYn().equals("Y")) booleanBuilder.and(hospital.holidayCare.eq("Y"));
        if(dto.fullTimeCareYn() != null && dto.fullTimeCareYn().equals("Y")) booleanBuilder.and(hospital.fullTimeCare.eq("Y"));

        NumberExpression<Long> bookmarkCountExpression = Expressions.asNumber(JPAExpressions
                .select(bookmark.count())
                .from(bookmark)
                .where(hospital.id.eq(bookmark.hospital.id)));

        OrderSpecifier[] orderSpecifier = createOrderSpecifier(dto.sortType(), distanceExpression, hospital, bookmark, bookmarkCountExpression);

        List<HospitalResponse> hospitalResult = jpaQueryFactory
                .selectDistinct(Projections.constructor(HospitalResponse.class,
                        hospital.id,
                        hospital.hospitalName,
                        workStatusExpression.as("workStatus"),
                        hospitalHour.dayOfWeek,
                        hospitalHour.openTime,
                        hospitalHour.closeTime,
                        distanceExpression.as("distance"),
                        hospital.address,
                        new CaseBuilder().when(bookmark.state.isNull()).then("N").otherwise(bookmark.state).as("bookmarkState")
                ))
                .from(hospital)
                .leftJoin(hospitalHour).on(hospital.eq(hospitalHour.hospital).and(hospitalHour.dayOfWeek.eq(day)))
                .leftJoin(hospitalService).on(hospital.eq(hospitalService.hospital))
                .leftJoin(bookmark).on(hospital.id.eq(bookmark.hospital.id).and(bookmark.user.id.eq(userId)))
                .where(booleanBuilder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = jpaQueryFactory.select(hospital.count())
                .from(hospital)
                .leftJoin(hospitalHour).on(hospital.eq(hospitalHour.hospital).and(hospitalHour.dayOfWeek.eq(day)))
                .leftJoin(hospitalService).on(hospital.eq(hospitalService.hospital))
                .leftJoin(bookmark).on(hospital.id.eq(bookmark.hospital.id).and(bookmark.user.id.eq(userId)))
                .where(booleanBuilder);

        List<Long> hospitalIds = hospitalResult.stream().map(HospitalResponse::getId).toList();
        List<HospitalServiceEntity> services = jpaQueryFactory
                .select(hospitalService)
                .from(hospitalService)
                .where(hospitalService.hospital.id.in(hospitalIds).and(hospitalService.serviceStatus.eq("Y")))
                .fetch();

        Map<Long, List<ServiceType>> servicesMap = services.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.getHospital().getId(),
                        Collectors.mapping(HospitalServiceEntity::getServiceName, Collectors.toList())
                ));

        hospitalResult.forEach(hospitalResponse -> hospitalResponse.setServices(servicesMap.get(hospitalResponse.getId())));

        return PageableExecutionUtils.getPage(hospitalResult, pageable, countQuery::fetchOne);
    }

    @Override
    public Long countTotalHospitals(SearchHospitalRequestDto dto) {
        return hospitalRepository.count();
    }

    @Override
    public HospitalEntity findById(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 병원이 존재하지 않습니다."));
    }

    @Override
    public List<HospitalResponse> findBookmarkedHospitals(Long userId, Double latitude, Double longitude) {
        QHospitalEntity hospital = QHospitalEntity.hospitalEntity;
        QHospitalHourEntity hospitalHour = QHospitalHourEntity.hospitalHourEntity;
        QHospitalServiceEntity hospitalService = QHospitalServiceEntity.hospitalServiceEntity;
        QBookmarkEntity bookmark = QBookmarkEntity.bookmarkEntity;

        LocalDate today = LocalDate.now();
        // 오늘 요일 추출
        DayOfWeek day = DayOfWeek.fromJavaDayOfWeek(today.getDayOfWeek());

        // 거리 계산 식
        NumberExpression<Integer> distanceExpression = Expressions.numberTemplate(Integer.class,
                "round(6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1}))))",
                latitude, hospital.latitude, hospital.longitude, longitude);

        // 현재 시간
        TimeTemplate<LocalTime> now = Expressions.timeTemplate(LocalTime.class, "now()");

        // 진료 상태 계산
        StringExpression workStatusExpression = new CaseBuilder()
                .when(now.between(hospitalHour.openTime, hospitalHour.closeTime))
                .then("진료중")
                .otherwise("진료 종료");

        List<HospitalResponse> hospitalResult = jpaQueryFactory
                .selectDistinct(Projections.constructor(HospitalResponse.class,
                        hospital.id,
                        hospital.hospitalName,
                        workStatusExpression.as("workStatus"),
                        hospitalHour.dayOfWeek,
                        hospitalHour.openTime,
                        hospitalHour.closeTime,
                        distanceExpression.as("distance"),
                        hospital.address,
                        new CaseBuilder().when(bookmark.state.isNull()).then("N").otherwise(bookmark.state).as("bookmarkState")
                ))
                .from(hospital)
                .leftJoin(hospitalHour).on(hospital.eq(hospitalHour.hospital).and(hospitalHour.dayOfWeek.eq(day)))
                .leftJoin(hospitalService).on(hospital.eq(hospitalService.hospital))
                .leftJoin(bookmark).on(hospital.id.eq(bookmark.hospital.id).and(bookmark.user.id.eq(userId)))
                .where(bookmark.state.eq("Y"))
                .orderBy(distanceExpression.asc())
                .fetch();

        List<Long> hospitalIds = hospitalResult.stream().map(HospitalResponse::getId).toList();
        List<HospitalServiceEntity> services = jpaQueryFactory
                .select(hospitalService)
                .from(hospitalService)
                .where(hospitalService.hospital.id.in(hospitalIds).and(hospitalService.serviceStatus.eq("Y")))
                .fetch();

        Map<Long, List<ServiceType>> servicesMap = services.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.getHospital().getId(),
                        Collectors.mapping(HospitalServiceEntity::getServiceName, Collectors.toList())
                ));

        hospitalResult.forEach(hospitalResponse -> hospitalResponse.setServices(servicesMap.get(hospitalResponse.getId())));
        return hospitalResult;
    }

    @Override
    public List<HospitalEntity> findHospitalsByTitle(String title) {
        QHospitalEntity hospital = QHospitalEntity.hospitalEntity;
        QHospitalServiceEntity hospitalService = QHospitalServiceEntity.hospitalServiceEntity;

        return jpaQueryFactory.select(hospital)
                .from(hospital)
                .leftJoin(hospital.hospitalServices, hospitalService).fetchJoin()
                .where(hospital.hospitalName.like("%" + title + "%"))
                .orderBy(hospital.hospitalName.asc())
                .fetch();
    }

    @Override
    public HospitalEntity findHospitalDetailInfoById(Long hospitalId) {
        return hospitalRepository.findDetailById(hospitalId).orElse(null);
    }

    private OrderSpecifier[] createOrderSpecifier(String sortType, NumberExpression<Integer> distanceExpression, QHospitalEntity hospital, QBookmarkEntity bookmark, NumberExpression<Long> bookmarkCountExpression) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(sortType.equals("distance")) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, distanceExpression));
        }else if(sortType.equals("name")) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.ASC, hospital.hospitalName));
        }else if(sortType.equals("bookmark")) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, bookmarkCountExpression));
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, distanceExpression));
        }else {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, distanceExpression));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
