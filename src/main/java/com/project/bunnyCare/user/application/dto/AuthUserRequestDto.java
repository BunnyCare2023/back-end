package com.project.bunnyCare.user.application.dto;

import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.User;

public record AuthUserRequestDto(String email, String name, SocialType socialType) {

    public User toUser(){
        return User.builder()
                .email(email())
                .name(name())
                .socialType(socialType())
                .build();
    }
}
