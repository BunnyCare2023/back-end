package com.project.bunnyCare.hospital.infrastructure;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {

}
