package com.project.bunnyCare.common.exception;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.api.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Order(1000)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> exceptionHandler(Exception e) {
        log.error("GlobalExceptionHandler: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponse.badRequest("잘못된 요청입니다. 다시 시도해주세요"));
    }
}
