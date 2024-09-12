package com.project.bunnyCare.hospital.domain.hospitalService;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "hospital_service")
@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalServiceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceName;

    private String serviceStatus; // C , Y, N, M,F

    @ManyToOne
    private HospitalEntity hospital;
}
