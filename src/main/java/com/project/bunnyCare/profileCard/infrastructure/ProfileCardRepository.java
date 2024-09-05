package com.project.bunnyCare.profileCard.infrastructure;

import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileCardRepository extends JpaRepository<ProfileCardEntity, Long> {
    List<ProfileCardEntity> findAllByUserId(Long userId);
}
