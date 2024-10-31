package com.project.bunnyCare.common.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private ApiResult result;
    private T data;

    public ApiResponse(ApiResult result, T data) {
        this.result = result;
        this.data = data;
    }

    public static ApiResponse<Void> exception(ResponseCode code) {
        return new ApiResponse<>(ApiResult.setResult(code), null);
    }
    public static ApiResponse<Void> ok(ResponseCode code) {
        return new ApiResponse<>(ApiResult.setResult(code), null);
    }

    public static <T>ApiResponse<T> ok(ResponseCode code, T data) {
        return new ApiResponse<>(ApiResult.setResult(code), data);
    }

    public static ApiResponse<Void> badRequest(String message) {
        return new ApiResponse<>(ApiResult.badRequest(message), null);
    }
}
