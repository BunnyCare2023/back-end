package com.project.bunnyCare.hospital.application;

import com.project.bunnyCare.common.PageInfo;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.hospital.domain.HospitalStore;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseWithPageInfoDto;
import com.project.bunnyCare.hospital.interfaces.dto.CreateHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalRequestDto;
import com.project.bunnyCare.hospital.interfaces.dto.SearchHospitalResponseDto;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {

    private final UserReader userReader;
    private final HospitalMapper hospitalMapper;
    private final HospitalStore hospitalStore;
    private final HospitalReader hospitalReader;

    @Transactional
    public void createHospital(CreateHospitalRequestDto dto) {
        log.info("createHospital: dto={}", dto);
        Long userId = AuthUtil.getUserId();
        UserEntity user = userReader.findById(userId);

        HospitalEntity newHospital = hospitalMapper.toEntity(dto);
        hospitalStore.save(newHospital);
    }

    public SearchHospitalResponseWithPageInfoDto getHospitals(SearchHospitalRequestDto dto) {
        List<SearchHospitalResponseDto> responseList =  hospitalReader.findHospitalsForSearch(dto)
                .stream()
                .map(SearchHospitalResponseDto::of)
                .toList();
        Long totalCount = hospitalReader.countTotalHospitals(dto);
        PageInfo response = new PageInfo(dto.currentPage(), dto.pageSize(), totalCount);
        return new SearchHospitalResponseWithPageInfoDto(response,responseList);
    }
}
