package com.project.bunnyCare.user.application;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.common.jwt.TokenUtil;
import com.project.bunnyCare.user.domain.*;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.interfaces.dto.JwtResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@Disabled
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserReader userReader;

    @Mock
    private UserStore userStore;

    @Mock
    private TokenUtil tokenUtil;

    @Mock
    private UserMapper userMapper;

    private AuthUserRequestDto dto = new AuthUserRequestDto("email","name", SocialType.GOOGLE);
    private UserEntity user = UserEntity.builder().email("email").name("name").id(1L).role(Role.ROLE_USER).socialType(SocialType.GOOGLE).build();

    @Test
    @DisplayName("신규 회원가입")
    void givenAuthUserRequest_whenAuthUser_thenReturnJwtResponse () {
        //Given
        given(userReader.findByEmailAndSocialType(dto.email(), dto.socialType())).willReturn(null);
        given(userMapper.toEntity(dto)).willReturn(user);
        given(tokenUtil.issueAccessToken(any())).willReturn("accessToken");
        given(tokenUtil.issueRefreshToken(any())).willReturn("refreshToken");

        //When
        JwtResponseDto response = userService.authUser(dto);

        //Then
        assertEquals("accessToken", response.accessToken());
        assertEquals("refreshToken", response.refreshToken());
    }

    @Test
    @DisplayName("이미 삭제된 회원인 경우 예외 발생")
    void givenAuthUserRequest_whenDeletedUser_thenThrowException () {
        //Given
        given(userReader.findByEmailAndSocialType(dto.email(), dto.socialType())).willReturn(UserEntity.builder().deletedYn("Y").build());
        //When
        //Then
        assertThrows(ApiException.class, () -> userService.authUser(dto));
    }

    @Test
    @DisplayName("AccessToken 재발급")
    void givenRefreshToken_whenIssueAccessToken_thenReturnJwtResponse () {
        //Given
        String refreshToken = tokenUtil.issueRefreshToken(user);
        given(userReader.findByRefreshToken(refreshToken)).willReturn(user);
        given(tokenUtil.issueAccessToken(user)).willReturn("accessToken");
        given(tokenUtil.issueRefreshToken(user)).willReturn("refreshToken");
        //When
        JwtResponseDto response = userService.issueAccessToken(refreshToken, null);

        //Then
        assertEquals("accessToken", response.accessToken());
        assertEquals("refreshToken", response.refreshToken());
    }
    
    @Test
    @DisplayName("AccessToken 재발급 실패")
    void givenRefreshToken_whenIssueAccessToken_thenThrowException () {
        //Given
        String refreshToken = "refreshToken";
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        doThrow(ApiException.class).when(tokenUtil).validateToken(refreshToken);
        //When
        //Then
        assertThrows(ApiException.class, () -> userService.issueAccessToken(refreshToken,mockRequest));
    }

}