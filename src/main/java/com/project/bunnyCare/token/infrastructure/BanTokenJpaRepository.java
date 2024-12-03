package com.project.bunnyCare.token.infrastructure;

import com.project.bunnyCare.token.domain.BanToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BanTokenJpaRepository extends JpaRepository<BanToken, Long> {
    Optional<BanToken> findByToken(String token);
    Integer deleteAllByExpiredAtBefore(LocalDate date);
}
