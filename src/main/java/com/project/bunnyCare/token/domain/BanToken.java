package com.project.bunnyCare.token.domain;

import com.project.bunnyCare.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(indexes = {
        @Index(name = "idx_token", columnList = "token")
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BanToken extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDate expiredAt;

    private Long userId;

    public BanToken(String token, LocalDate expiredAt, Long userId) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.userId = userId;
    }
}
