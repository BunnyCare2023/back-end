package com.project.bunnyCare.hospital.domain.hospitalHour;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MONDAY("월요일"), TUESDAY("화요일"), WEDNESDAY("수요일"), THURSDAY("목요일"),
    FRIDAY("금요일"), SATURDAY("토요일"), SUNDAY("일요일"), HOLIDAY("공휴일");
    
    private String value;

   

    public static DayOfWeek fromJavaDayOfWeek(java.time.DayOfWeek javaDayOfWeek) {
        return switch (javaDayOfWeek) {
            case MONDAY -> MONDAY;
            case TUESDAY -> TUESDAY;
            case WEDNESDAY -> WEDNESDAY;
            case THURSDAY -> THURSDAY;
            case FRIDAY -> FRIDAY;
            case SATURDAY -> SATURDAY;
            case SUNDAY -> SUNDAY;
            default -> throw new IllegalArgumentException("Unknown day of week: " + javaDayOfWeek);
        };
    }


}
