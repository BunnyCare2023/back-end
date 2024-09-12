package com.project.bunnyCare.hospital.application;

import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalStore;
import com.project.bunnyCare.hospital.interfaces.dto.CreateHospitalRequestDto;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {

    private final UserReader userReader;
    private final HospitalMapper hospitalMapper;
    private final HospitalStore hospitalStore;

    @Transactional
    public void createHospital(CreateHospitalRequestDto dto) {
        log.info("createHospital: dto={}", dto);
        Long userId = AuthUtil.getUserId();
        UserEntity user = userReader.findById(userId);

        HospitalEntity newHospital = hospitalMapper.toEntity(dto);
        hospitalStore.save(newHospital);
    }
}
