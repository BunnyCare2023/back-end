package com.project.bunnyCare.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SocialType {
    GOOGLE("구글"),
    APPLE("애플");
    private String value;
}
