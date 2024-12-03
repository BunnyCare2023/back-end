package com.project.bunnyCare.token.application;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.common.jwt.TokenUtil;
import com.project.bunnyCare.token.domain.BanToken;
import com.project.bunnyCare.token.domain.BanTokenResponseCode;
import com.project.bunnyCare.token.infrastructure.BanTokenJpaRepository;
import com.project.bunnyCare.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BanTokenService {

    private final BanTokenJpaRepository banTokenJpaRepository;
    private final TokenUtil tokenUtil;

    // ban에 추가
    @Transactional
    public void saveBanToken(UserEntity user){
        BanToken banToken = new BanToken(user.getRefreshToken(), tokenUtil.getExpiredDate(user.getRefreshToken()), user.getId());
        banTokenJpaRepository.save(banToken);
    }

    // ban에 있는지 조회
    @Transactional(readOnly = true)
    public void searchBanToken(String token){
        BanToken banToken = banTokenJpaRepository.findByToken(token)
                .orElse(null);

        if(banToken != null) throw new ApiException(BanTokenResponseCode.NOT_ALLOWED_TOKEN);
    }
    //특정일 지나면 제거
    @Transactional
    public Integer deleteTokensBefore(LocalDate date){
        return banTokenJpaRepository.deleteAllByExpiredAtBefore(date);
    }
}
