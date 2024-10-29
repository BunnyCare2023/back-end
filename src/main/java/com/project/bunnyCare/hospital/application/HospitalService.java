package com.project.bunnyCare.hospital.application;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkReader;
import com.project.bunnyCare.common.PageInfo;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.hospital.domain.HospitalStore;
import com.project.bunnyCare.hospital.domain.hospitalBreakTime.HospitalBreakTimeEntity;
import com.project.bunnyCare.hospital.domain.hospitalHour.HospitalHourEntity;
import com.project.bunnyCare.hospital.domain.hospitalService.HospitalServiceEntity;
import com.project.bunnyCare.hospital.interfaces.dto.*;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final BookmarkReader bookmarkReader;

    @Transactional
    public void createHospital(CreateHospitalRequestDto dto) {
        log.info("createHospital: dto={}", dto);
        Long userId = AuthUtil.getUserId();
        UserEntity user = userReader.findById(userId);

        HospitalEntity newHospital = hospitalMapper.toEntity(dto);
        hospitalStore.save(newHospital);
    }

    @Transactional(readOnly = true)
    public  Page<SearchHospitalResponseDto> getHospitals(SearchHospitalRequestDto dto, Pageable pageable) {
        Page<SearchHospitalResponseDto> responseList =  hospitalReader.findHospitalsForSearch(dto, pageable)
                .map(SearchHospitalResponseDto::of);
        return responseList;
    }

    @Transactional(readOnly = true)
    public List<SearchHospitalResponseDto> getBookmarkedHospitals(Long userId, Double latitude, Double longitude) {
        List<SearchHospitalResponseDto> resultList = hospitalReader.findBookmarkedHospitals(userId, latitude, longitude)
                .stream().map(SearchHospitalResponseDto::of).toList();
        return resultList;
    }

    @Transactional(readOnly = true)
    public List<HospitalShortResponse> getHospitalsByTitle(String title) {

        return hospitalReader.findHospitalsByTitle(title)
                .stream().map(HospitalShortResponse::of).toList();

    }

    @Transactional
    public HospitalDetailResponse getDetailHospital(Long hospitalId) {
        HospitalEntity hospital = hospitalReader.findHospitalDetailInfoById(hospitalId);
        BookmarkEntity bookmark = bookmarkReader.findByUserIdAndHospitalId(AuthUtil.getUserId(), hospitalId);
        return HospitalDetailResponse.of(hospital, bookmark);
    }
}
