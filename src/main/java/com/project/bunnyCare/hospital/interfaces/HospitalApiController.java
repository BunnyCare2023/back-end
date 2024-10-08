package com.project.bunnyCare.hospital.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.hospital.application.HospitalService;
import com.project.bunnyCare.hospital.domain.HospitalResponseCode;
import com.project.bunnyCare.hospital.interfaces.dto.CreateHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.HospitalResponse;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/hospitals")
public class HospitalApiController {

    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createHospital(
            @RequestBody CreateHospitalRequestDto dto
            ) {
        hospitalService.createHospital(dto);
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.CREATE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SearchHospitalResponseWithPageInfoDto>> getHospitals(
            @ModelAttribute SearchHospitalRequestDto dto
            ) {
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.GET_SUCCESS, hospitalService.getHospitals(dto)));
    }
}
