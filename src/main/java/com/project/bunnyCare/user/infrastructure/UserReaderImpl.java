package com.project.bunnyCare.user.infrastructure;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import com.project.bunnyCare.user.domain.UserResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;

    @Override
    public UserEntity findByEmailAndSocialType(String email, SocialType socialType) {
        return userRepository.findByEmailAndSocialType(email, socialType).orElse(null);
    }

    @Override
    public UserEntity findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ApiException(UserResponseCode.INVALID_REFRESH_TOKEN));
    }
}
