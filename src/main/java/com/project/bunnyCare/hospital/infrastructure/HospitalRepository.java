package com.project.bunnyCare.hospital.infrastructure;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {


    @Query("select h from HospitalEntity h where h.id = :hospitalId and h.deleteYn = 'N'")
    Optional<HospitalEntity> findDetailById(Long hospitalId);
}
