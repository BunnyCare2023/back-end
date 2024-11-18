package com.project.bunnyCare.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.user.domain.UserResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final TokenUtil tokenUtil;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        UserResponseCode errorResponse = (UserResponseCode) request.getAttribute("error");
        String token = tokenUtil.parseBearerToken(request);
        log.warn("AuthenticationEntryPoint token: {}, message: {}",token , errorResponse.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper obj = new ObjectMapper();
        ApiResponse<Void> api = ApiResponse.exception(errorResponse);
        response.getWriter().write(obj.writeValueAsString(api));
    }
}
