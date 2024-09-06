package com.project.bunnyCare.profileCard.infrastructure;

import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileCardRepository extends JpaRepository<ProfileCardEntity, Long> {
    List<ProfileCardEntity> findAllByUserIdAndDeleteYn(Long user_id, Character deleteYn);
    Optional<ProfileCardEntity> findByIdAndDeleteYn(Long id, Character deleteYn);
}
