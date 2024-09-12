package com.project.bunnyCare.hospital.domain.hospitalHour;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Table(name = "hospital_hour")
@Entity
@ToString
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

    private Time openTime;

    private Time closeTime;

    @ManyToOne
    private HospitalEntity hospital;
}
