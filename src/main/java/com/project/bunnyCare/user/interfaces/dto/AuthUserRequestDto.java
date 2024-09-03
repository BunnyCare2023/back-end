package com.project.bunnyCare.user.interfaces.dto;

import com.project.bunnyCare.user.domain.SocialType;

public record AuthUserRequestDto(String email, String name, SocialType socialType) {

}
