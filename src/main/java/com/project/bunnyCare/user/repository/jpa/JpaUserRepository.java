package com.project.bunnyCare.user.repository.jpa;

import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndSocialType(String email, SocialType socialType);
}
