package com.project.bunnyCare.hospital.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bunnyCare.hospital.domain.hospitalHour.DayOfWeek;
import com.project.bunnyCare.hospital.domain.hospitalService.ServiceType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@ToString
public class HospitalResponse {
    private Long id;
    private String hospitalName;
    private String workStatus;
    private DayOfWeek dayOfWeek;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;
    private Integer distance;
    private String address;
    private List<ServiceType> services;
    private String bookmarkState;



    public HospitalResponse() {
    }

    public HospitalResponse(Long id, String hospitalName, String workStatus, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, Integer distance, String address, String  bookmarkState) {
        this.id = id;
        this.hospitalName = hospitalName;
        this.workStatus = workStatus;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.distance = distance;
        this.address = address;
        this.services = List.of();
        this.bookmarkState = bookmarkState;
    }

    public void setServices(List<ServiceType> services) {
        this.services = services;
    }
}
