package com.project.bunnyCare.hospital.domain.hospitalHour;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Table(name = "hospital_hour")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalHourEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer orderNo;

    @ManyToOne
    private HospitalEntity hospital;
}
