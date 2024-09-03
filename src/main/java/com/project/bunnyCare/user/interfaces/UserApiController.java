package com.project.bunnyCare.user.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.CookieUtil;
import com.project.bunnyCare.user.application.UserMapper;
import com.project.bunnyCare.user.application.UserService;
import com.project.bunnyCare.user.interfaces.dto.AccessTokenResponseDto;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.interfaces.dto.JwtResponseDto;
import com.project.bunnyCare.user.domain.UserResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}")
public class UserApiController {

    private final UserService userService;

    @Value("${jwt.refresh.hour}")
    private int refreshHour;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<AccessTokenResponseDto>> authUser(@RequestBody AuthUserRequestDto dto, HttpServletResponse response) {
        JwtResponseDto tokens = userService.authUser(dto);
        CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), refreshHour);
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.AUTH_SUCCESS, new AccessTokenResponseDto(tokens.accessToken())));
    }

    @GetMapping("/auth/issue-access-token")
    public ResponseEntity<ApiResponse<AccessTokenResponseDto>> issueAccessToken(
            @CookieValue(required = false, value = "refreshToken") String refreshToken,
            HttpServletResponse response,
            HttpServletRequest request
            ) {

        JwtResponseDto tokens = userService.issueAccessToken(refreshToken, request);
        CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), refreshHour);
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.ISSUE_ACCESS_TOKEN_SUCCESS, new AccessTokenResponseDto(tokens.accessToken())));
    }

}
