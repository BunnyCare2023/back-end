package com.project.bunnyCare.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResult {
    private int code;
    private String status;
    private String message;

    public static ApiResult setResult(ResponseCode code) {
        return new ApiResult(code.getCode(), code.getStatus(), code.getMessage());
    }
}
