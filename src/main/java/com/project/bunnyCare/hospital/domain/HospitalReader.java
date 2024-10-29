package com.project.bunnyCare.hospital.domain;

import com.project.bunnyCare.hospital.interfaces.dto.HospitalResponse;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HospitalReader {

    Page<HospitalResponse> findHospitalsForSearch(SearchHospitalRequestDto dto, Pageable pageable);

    Long countTotalHospitals(SearchHospitalRequestDto dto);

    HospitalEntity findById(Long hospitalId);

    List<HospitalResponse> findBookmarkedHospitals(Long userId, Double latitude, Double longitude);

    List<HospitalEntity> findHospitalsByTitle(String title);

    HospitalEntity findHospitalDetailInfoById(Long hospitalId);
}
