package com.project.bunnyCare.common.exception;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.LogKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @Order(1)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> apiExceptionHandler(ApiException e) {
        log.error("CustomExceptionHandler key: {}, message: {}", LogKeyUtil.getLogKey(),e.getMessage());
        return ResponseEntity.status(e.getCode().getCode()).body(ApiResponse.exception(e.getCode()));
    }
}
