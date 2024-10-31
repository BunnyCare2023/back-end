package com.project.bunnyCare.user.application;


import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.common.jwt.TokenUtil;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import com.project.bunnyCare.user.domain.UserResponseCode;
import com.project.bunnyCare.user.domain.UserStore;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
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

    @Transactional
    public JwtResponseDto authUser(AuthUserRequestDto dto) {
        UserEntity user = userReader.findByEmailAndSocialType(dto.email(), dto.socialType());
        if(user != null && user.getDeletedYn().equals("Y")){
            throw new ApiException(UserResponseCode.DELETED_USER);
        }
        // 없으면 생성
        if(user == null) {
            user = userMapper.toEntity(dto);
            user.registerNewUser();
            userStore.save(user);
        }

        // 토큰 발급
        String accessToken = tokenUtil.issueAccessToken(user);
        String refreshToken = tokenUtil.issueRefreshToken(user);

        user.setRefreshToken(refreshToken);

        return new JwtResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public JwtResponseDto issueAccessToken(String refreshToken, HttpServletRequest request) {
        UserEntity user = userReader.findByRefreshToken(refreshToken);
        tokenUtil.validateToken(refreshToken);

        String newAccessToken = tokenUtil.issueAccessToken(user);
        String newRefreshToken = tokenUtil.issueRefreshToken(user);

        user.setRefreshToken(newRefreshToken);

        return new JwtResponseDto(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void deleteUser() {
        Long userId = AuthUtil.getUserId();
        // 유저와 관련된 모든 정보 삭제해야하나?
        UserEntity user = userReader.findById(userId);
        user.delete();
    }
}
