package com.project.bunnyCare.feedback.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeedbackResponseCode implements ResponseCode {

    CREATE_SUCCESS(200, "OK", "의견이 성공적으로 등록되었습니다."),
    READ_SUCCESS(200, "OK", "의견 목록을 성공적으로 조회하였습니다."),
    READ_DETAIL_FAIL(404, "NOT_FOUND", "의견 상세 조회에 실패하였습니다.");

    private int code;
    private String status;
    private String message;
}
