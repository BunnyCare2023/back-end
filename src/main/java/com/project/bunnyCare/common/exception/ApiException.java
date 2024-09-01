package com.project.bunnyCare.common.exception;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException{
    private final ResponseCode code;
}
