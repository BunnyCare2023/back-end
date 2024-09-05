package com.project.bunnyCare.user.domain;

public interface UserReader {
    UserEntity findByEmailAndSocialType(String email, SocialType socialType);

    UserEntity findByRefreshToken(String refreshToken);

    UserEntity findById(Long userId);
}
