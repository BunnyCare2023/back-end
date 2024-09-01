package com.project.bunnyCare.common.api;

public class ApiResponse<T> {
    private final ApiResult result;
    private final T data;

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
}
