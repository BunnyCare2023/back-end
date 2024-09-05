package com.project.bunnyCare.image.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageResponseCode implements ResponseCode {

    IMAGE_UPLOAD_FAILED(1001, "FAILED", "이미지 업로드에 실패했습니다."),
    IMAGE_DELETE_FAILED(1002, "FAILED", "이미지 삭제에 실패했습니다.");

    private int code;
    private String status;
    private String message;
}
