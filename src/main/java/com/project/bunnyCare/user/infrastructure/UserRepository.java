package com.project.bunnyCare.user.infrastructure;

import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndSocialType(String email, SocialType socialType);
    Optional<UserEntity> findByRefreshToken(String refreshToken);
}
