package com.project.bunnyCare.user.ui;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.CookieUtil;
import com.project.bunnyCare.user.application.UserService;
import com.project.bunnyCare.user.application.dto.AccessTokenResponseDto;
import com.project.bunnyCare.user.application.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.application.dto.JwtResponseDto;
import com.project.bunnyCare.user.domain.UserResponseCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

}
