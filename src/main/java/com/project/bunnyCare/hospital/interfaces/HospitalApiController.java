package com.project.bunnyCare.hospital.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.hospital.application.HospitalService;
import com.project.bunnyCare.hospital.domain.HospitalResponseCode;
import com.project.bunnyCare.hospital.interfaces.dto.CreateHospitalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
