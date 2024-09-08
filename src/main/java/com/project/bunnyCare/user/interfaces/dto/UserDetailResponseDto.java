package com.project.bunnyCare.user.interfaces.dto;

import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.UserEntity;

public record UserDetailResponseDto(
        Long id,
        String email,
        String name,
        SocialType socialType
) {

    public static UserDetailResponseDto from(UserEntity userEntity) {
        return new UserDetailResponseDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getSocialType()
        );
    }
}
