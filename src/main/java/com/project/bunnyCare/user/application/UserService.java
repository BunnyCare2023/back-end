package com.project.bunnyCare.user.application;


import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.common.exception.JwtAuthenticationException;
import com.project.bunnyCare.common.infrastructure.TelegramNotificationService;
import com.project.bunnyCare.common.jwt.TokenUtil;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.token.application.BanTokenService;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import com.project.bunnyCare.user.domain.UserResponseCode;
import com.project.bunnyCare.user.domain.UserStore;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.interfaces.dto.DeleteUserRequest;
import com.project.bunnyCare.user.interfaces.dto.JwtResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReader userReader;
    private final UserStore userStore;
    private final TokenUtil tokenUtil;
    private final UserMapper userMapper;
    private final TelegramNotificationService telegramNotificationService;
    private final BanTokenService banTokenService;

    @Transactional
    public JwtResponseDto authUser(AuthUserRequestDto dto) {
        UserEntity user = userReader.findByEmailAndSocialType(dto.email(), dto.socialType());
        if(user != null && user.isDeleted()){
            throw new ApiException(UserResponseCode.DELETED_USER);
        }

        if(user == null) {
            user = userMapper.createUser(dto);
            user = userStore.save(user);
            // 비동기로 api 호출해야함.
            telegramNotificationService.sendRegisterMessage(user);
        }else {
            banTokenService.saveBanToken(user);
        }
        // 토큰 발급
        JwtResponseDto jwtResponse = tokenUtil.issueTokens(user);
        user.changeRefreshToken(jwtResponse.refreshToken());
        return jwtResponse;
    }

    @Transactional
    public JwtResponseDto issueAccessToken(String refreshToken) {
        if(refreshToken == null) {
            throw new ApiException(UserResponseCode.INVALID_REFRESH_TOKEN);
        }
        try{
            tokenUtil.validateToken(refreshToken);
        }catch (Exception e){
            throw new ApiException(UserResponseCode.INVALID_REFRESH_TOKEN);
        }
        banTokenService.searchBanToken(refreshToken);
        UserEntity user = userReader.findByRefreshToken(refreshToken);
        banTokenService.saveBanToken(user);

        JwtResponseDto jwtResponse = tokenUtil.issueTokens(user);
        user.changeRefreshToken(jwtResponse.refreshToken());
        return jwtResponse;
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request) {
        Long userId = AuthUtil.getUserId();
        // 유저와 관련된 모든 정보 삭제해야하나?
        UserEntity user = userReader.findById(userId);
        banTokenService.saveBanToken(user);
        user.delete(request.deletedReason());
        telegramNotificationService.sendDeleteMessage(user,request.deletedReason());
    }
}
