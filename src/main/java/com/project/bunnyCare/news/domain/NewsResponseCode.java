package com.project.bunnyCare.news.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NewsResponseCode implements ResponseCode {
    CREATE(200, "created", "뉴스가 성공적으로 생성되었습니다."),
    NOT_FOUND(404, "not found", "뉴스를 찾을 수 없습니다."),
    UPDATE(200, "updated", "뉴스가 성공적으로 수정되었습니다."),
    SUCCESS(200, "success", "뉴스가 성공적으로 조회되었습니다."),
    DELETE(200, "deleted", "뉴스가 성공적으로 삭제되었습니다.");

    private int code;
    private String status;
    private String message;
}
