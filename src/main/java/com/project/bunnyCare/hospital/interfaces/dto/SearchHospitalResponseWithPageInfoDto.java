package com.project.bunnyCare.hospital.interfaces.dto;

import com.project.bunnyCare.common.PageInfo;

import java.util.List;

public record SearchHospitalResponseWithPageInfoDto(
        PageInfo pageInfo,
        List<SearchHospitalResponseDto> searchHospitalResponseDto
) {

}
