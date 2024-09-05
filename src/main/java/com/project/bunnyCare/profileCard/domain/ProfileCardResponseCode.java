package com.project.bunnyCare.profileCard.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileCardResponseCode implements ResponseCode {

    CRATED(201, "CREATED", "프로필 카드가 생성되었습니다."),
    NOT_FOUND(404, "NOT_FOUND", "프로필 카드를 찾을 수 없습니다."),
    FORBIDDEN(403, "FORBIDDEN", "프로필 카드에 접근할 권한이 없습니다."),
    DELETE(200, "OK", "프로필 카드가 삭제되었습니다."),
    UPDATE(200, "OK", "프로필 카드가 수정되었습니다."),
    READ(200, "OK", "프로필 카드를 조회하였습니다.");

    private int code;
    private String status;
    private String message;
}
