package com.project.bunnyCare.common.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResult {
    private final int code;
    private final String status;
    private final String message;

    public static ApiResult setResult(ResponseCode code) {
        return new ApiResult(code.getCode(), code.getStatus(), code.getMessage());
    }
}
