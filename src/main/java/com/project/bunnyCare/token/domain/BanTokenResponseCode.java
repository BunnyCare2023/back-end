package com.project.bunnyCare.token.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BanTokenResponseCode implements ResponseCode {
    NOT_ALLOWED_TOKEN(401, "Unauthorized", "접근 금지된 토큰입니다.")
    ;
    private final int code;
    private final String status;
    private final String message;

}
