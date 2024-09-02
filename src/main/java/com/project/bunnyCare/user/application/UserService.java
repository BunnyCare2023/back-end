package com.project.bunnyCare.user.application;


import com.project.bunnyCare.common.jwt.TokenUtil;
import com.project.bunnyCare.user.application.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.application.dto.JwtResponseDto;
import com.project.bunnyCare.user.application.interfaces.UserRepository;
import com.project.bunnyCare.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    public JwtResponseDto authUser(AuthUserRequestDto dto) {
        User user = userRepository.findByEmailAndSocialType(dto.email(), dto.socialType());
        // 없으면 생성
        if(user == null) {
           User newUser = dto.toUser();
           newUser.setUserRole();
           user = userRepository.save(newUser);
        }

        // 토큰 발급
        String accessToken = tokenUtil.issueAccessToken(user);
        String refreshToken = tokenUtil.issueRefreshToken(user);

        return new JwtResponseDto(accessToken, refreshToken);
    }

}
