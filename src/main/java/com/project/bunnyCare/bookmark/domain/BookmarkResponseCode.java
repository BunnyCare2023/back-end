package com.project.bunnyCare.bookmark.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookmarkResponseCode implements ResponseCode {
    LIKE_SUCCESS(200, "OK", "북마크 등록 성공"),
    GET_BOOKMARK_FAIL(404, "NOT_FOUND", "북마크 조회에 실패했습니다."),
    UNLIKE_SUCCESS(200, "OK", "북마크 취소 성공");


    private int code;
    private String status;
    private String message;
}
