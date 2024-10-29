package com.project.bunnyCare.hospital.interfaces.dto;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.HospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.interfaces.dto.hospitalBreakTime.HospitalBreakTimeResponseDto;
import com.project.bunnyCare.hospital.interfaces.dto.hospitalHour.HospitalHourResponseDto;
import com.project.bunnyCare.hospital.interfaces.dto.hospitalService.SearchHospitalServiceResponseDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HospitalDetailResponse {

    private Long id;

    private String dayOfWeek;

    private String openStatus;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String hospitalName;

    private String city;

    private String district;

    private String address;

    private String telNo;

    private String note;

    private Double latitude; //위도

    private Double longitude; //경도

    private String nightCare;   //야간진료

    private String sundayCare;  //일요일진료

    private String holidayCare; //공휴일진료

    private String fullTimeCare; //24시간진료

    private String deleteYn;

    private String bookmarkYn;

    List<SearchHospitalServiceResponseDto> hospitalServices;
    List<HospitalHourResponseDto> hospitalHours;
    List<HospitalBreakTimeResponseDto> hospitalBreakTimes;


    public static HospitalDetailResponse of(HospitalEntity entity, BookmarkEntity bookmark) {
        DayOfWeek dayOfWeek = DayOfWeek.fromJavaDayOfWeek(LocalDate.now().getDayOfWeek());
        List<HospitalHourResponseDto> hospitalHours = entity.getHospitalHours().stream().map(HospitalHourResponseDto::of).toList();

        LocalTime now = LocalTime.now();
        String status = "";
        LocalTime openTime = null;
        LocalTime closeTime = null;


        for(HospitalHourResponseDto dto : hospitalHours) {
            if(dayOfWeek.getValue().equals(dto.dayOfWeek())){
                if(now.isAfter(dto.openTime()) && now.isBefore(dto.closeTime())){
                    status = "진료중";
                }else {
                    status = "진료종료";
                }
                openTime = dto.openTime();
                closeTime = dto.closeTime();
                break;
            }
        }


        return HospitalDetailResponse.builder()
                .id(entity.getId())
                .dayOfWeek(dayOfWeek.getValue())
                .openStatus(status)
                .openTime(openTime)
                .closeTime(closeTime)
                .hospitalName(entity.getHospitalName())
                .city(entity.getCity())
                .district(entity.getDistrict())
                .address(entity.getAddress())
                .telNo(entity.getTelNo())
                .note(entity.getNote())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .nightCare(entity.getNightCare())
                .sundayCare(entity.getSundayCare())
                .holidayCare(entity.getHolidayCare())
                .fullTimeCare(entity.getFullTimeCare())
                .deleteYn(entity.getDeleteYn())
                .bookmarkYn(bookmark != null? bookmark.getState(): "N")
                .hospitalServices(entity.getHospitalServices().stream().map(SearchHospitalServiceResponseDto::of).toList())
                .hospitalHours(hospitalHours)
                .hospitalBreakTimes(entity.getHospitalBreakTimes().stream().map(HospitalBreakTimeResponseDto::of).toList())
                .build();
    }

}
