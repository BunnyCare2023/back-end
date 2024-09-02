package com.project.bunnyCare.user.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserResponseCode implements ResponseCode {
    NULL_POINTER_EXCEPTION(400, "fail", "NullPointerException"),
    MAL_FORMED_TOKEN(4000,"BAD REQUEST","잘못된 토큰 형식입니다"),
    EXPIRED_TOKEN(4001,"BAD REQUEST","만료된 토큰입니다"),
    INVALID_TOKEN(4002,"BAD REQUEST","유효하지 않은 토큰입니다"),
    UNSUPPORTED_TOKEN(4003,"BAD REQUEST","지원하지 않는 토큰입니다"),
    INVALID_REFRESH_TOKEN(4004,"BAD REQUEST","유효하지 않은 리프레시 토큰입니다"),
    UNAUTHORIZED(401,"UNAUTHORIZED","인증되지 않은 사용자입니다"),
    FORBIDDEN(403,"FORBIDDEN","접근 권한이 없습니다"),
    AUTH_SUCCESS(200,"OK","로그인 성공");

    private int code;
    private String status;
    private String message;
}
