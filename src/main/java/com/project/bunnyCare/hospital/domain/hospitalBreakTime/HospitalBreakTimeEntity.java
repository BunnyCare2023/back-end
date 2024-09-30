package com.project.bunnyCare.hospital.domain.hospitalBreakTime;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Table(name = "hospital_break_time")
@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalBreakTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeekType weekType;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer orderNo;

    @ManyToOne
    private HospitalEntity hospital;


}
