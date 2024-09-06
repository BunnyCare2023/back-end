package com.project.bunnyCare.profileCard.domain.appearance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppearanceType {
    MIX("믹스"),
    ETC("기타"),
    DWARVES("드워프"),
    DUTCH("더치"),
    MASCARA("마스카라"),
    LIONHEAD("라이언헤드"),
    LOPT_EAR("롭이어"),
    REX("렉스"),
    CHINCHILLA("친칠라"),
    GIANT("자이언트"),
    ANGOLA("앙골라"),
    ENGLISH_SPOT("잉글리쉬 스팟"),;
    private String name;
}
