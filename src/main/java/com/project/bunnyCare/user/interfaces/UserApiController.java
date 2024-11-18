package com.project.bunnyCare.user.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.CookieUtil;
import com.project.bunnyCare.user.application.UserService;
import com.project.bunnyCare.user.interfaces.dto.AccessTokenResponseDto;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
import com.project.bunnyCare.user.interfaces.dto.DeleteUserRequest;
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
@RequestMapping("/api/${api.version}/auth")
public class UserApiController {

    private final UserService userService;

    @Value("${jwt.refresh.hour}")
    private int refreshHour;

    @PostMapping
    public ResponseEntity<ApiResponse<AccessTokenResponseDto>> authUser(@RequestBody AuthUserRequestDto dto, HttpServletResponse response) {
        JwtResponseDto tokens = userService.authUser(dto);
        CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), refreshHour);
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.AUTH_SUCCESS, new AccessTokenResponseDto(tokens.accessToken())));
    }

    @GetMapping("/issue-access-token")
    public ResponseEntity<ApiResponse<AccessTokenResponseDto>> issueAccessToken(
            @CookieValue(required = false, value = "refreshToken") String refreshToken,
            HttpServletResponse response
            ) {

        JwtResponseDto tokens = userService.issueAccessToken(refreshToken);
        CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), refreshHour);
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.ISSUE_ACCESS_TOKEN_SUCCESS, new AccessTokenResponseDto(tokens.accessToken())));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        CookieUtil.removeCookie(response, "refreshToken");
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.LOGOUT_SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
        return ResponseEntity.ok(ApiResponse.ok(UserResponseCode.DELETE_SUCCESS));
    }

}
