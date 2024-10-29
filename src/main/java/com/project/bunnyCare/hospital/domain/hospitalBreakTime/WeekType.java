package com.project.bunnyCare.hospital.domain.hospitalBreakTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeekType {
    WEEKDAY("평일"), // 평일
    WEEKEND("주말"),
    FULLWEEK("매주");
    // 주말
    private String value;

}
