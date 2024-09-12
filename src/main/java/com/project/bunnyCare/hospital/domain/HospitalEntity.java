package com.project.bunnyCare.hospital.domain;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.HospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "hospital")
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HospitalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String city;

    private String district;

    private String address;

    private String telNo;

    private String note;

    private Double latitude; //위도

    private Double longitude; //경도

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HospitalServiceEntity> hospitalServices;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HospitalHourEntity> hospitalHours;

    public void update(List<HospitalServiceEntity> hospitalServices, List<HospitalHourEntity> hospitalHours){
        this.hospitalServices = hospitalServices;
        this.hospitalHours = hospitalHours;
    }

}
