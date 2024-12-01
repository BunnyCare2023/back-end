package com.project.bunnyCare.hospital.domain;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.HospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.HospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Table(name = "hospital")
@Entity
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

    private String nightCare;   //야간진료

    private String sundayCare;  //일요일진료

    private String holidayCare; //공휴일진료

    private String fullTimeCare; //24시간진료

    @Column(name = "delete_yn", columnDefinition = "char(1) default 'N'")
    private String deleteYn;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private List<HospitalServiceEntity> hospitalServices;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private List<HospitalHourEntity> hospitalHours;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private List<HospitalBreakTimeEntity> hospitalBreakTimes;

    @OneToMany(mappedBy = "hospitalId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarks;

    public void update(List<HospitalServiceEntity> hospitalServices, List<HospitalHourEntity> hospitalHours, List<HospitalBreakTimeEntity> hospitalBreakTimes) {
        this.hospitalServices = hospitalServices;
        this.hospitalHours = hospitalHours;
        this.hospitalBreakTimes = hospitalBreakTimes;
    }

}
