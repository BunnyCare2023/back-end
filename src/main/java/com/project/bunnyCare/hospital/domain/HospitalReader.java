package com.project.bunnyCare.hospital.domain;

import com.project.bunnyCare.hospital.interfaces.dto.HospitalResponse;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;

import java.util.List;

public interface HospitalReader {

    List<HospitalResponse> findHospitalsForSearch(SearchHospitalRequestDto dto);

}
