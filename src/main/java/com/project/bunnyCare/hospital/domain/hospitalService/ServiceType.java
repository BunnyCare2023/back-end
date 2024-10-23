package com.project.bunnyCare.hospital.domain.hospitalService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {
   CLIPPING("발톱깍이"), NEUTERING("중성화"), BLOOD_TEST("혈액검사"), XRAY("엑스레이"),
   HEALTHY_CHECK("건강검진"), VACCINATION("예방접종"), MALE_NEUTERING("수컷중성화"),
   FEMAILE_NEUTERING("암컷중성화"), TRIMMING("트리밍"), ETC("기타");

   private  String value;
}
