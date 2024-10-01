package com.project.bunnyCare.hospital.interfaces;

import com.project.bunnyCare.common.PageInfo;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;

import java.util.List;

public record SearchHospitalResponseWithPageInfoDto(
        PageInfo pageInfo,
        List<SearchHospitalResponseDto> searchHospitalResponseDto
) {

}
