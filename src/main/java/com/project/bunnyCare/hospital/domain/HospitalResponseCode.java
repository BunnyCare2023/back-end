package com.project.bunnyCare.hospital.domain;

import com.project.bunnyCare.common.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HospitalResponseCode implements ResponseCode {
    CREATE_SUCCESS(201, "Created", "병원이 성공적으로 생성되었습니다.");

    private int code;
    private String status;
    private String message;
}
