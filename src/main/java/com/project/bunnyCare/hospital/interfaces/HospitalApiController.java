package com.project.bunnyCare.hospital.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.hospital.application.HospitalService;
import com.project.bunnyCare.hospital.domain.HospitalResponseCode;
import com.project.bunnyCare.hospital.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
