package com.project.bunnyCare.hospital.infrastructure;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HospitalStoreImpl implements HospitalStore {

    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalEntity save(HospitalEntity hospitalEntity) {
        return hospitalRepository.save(hospitalEntity);
    }
}
