package com.project.bunnyCare.hospital.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.application.HospitalService;
import com.project.bunnyCare.hospital.domain.HospitalResponseCode;
import com.project.bunnyCare.hospital.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<Page<SearchHospitalResponseDto>>> getHospitals(
            @ModelAttribute SearchHospitalRequestDto dto,
            @PageableDefault(size = 10) Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.GET_SUCCESS, hospitalService.getHospitals(dto, pageable)));
    }


    @GetMapping("/bookmark")
    public ResponseEntity<ApiResponse<List<SearchHospitalResponseDto>>> getBookmarkedHospitals(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ){
        Long userId = AuthUtil.getUserId();
        List<SearchHospitalResponseDto> response = hospitalService.getBookmarkedHospitals(userId, latitude, longitude);
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.GET_SUCCESS, response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<HospitalShortResponse>>> getTitleSearchHospitals(
            @RequestParam String title
    ) {
        List<HospitalShortResponse> response = hospitalService.getHospitalsByTitle(title);
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.GET_SUCCESS, response));
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDetailResponse>> getDetailHospital(
            @PathVariable Long hospitalId
    ) {

        HospitalDetailResponse response = hospitalService.getDetailHospital(hospitalId);
        return ResponseEntity.ok(ApiResponse.ok(HospitalResponseCode.GET_SUCCESS, response));
    }
}
