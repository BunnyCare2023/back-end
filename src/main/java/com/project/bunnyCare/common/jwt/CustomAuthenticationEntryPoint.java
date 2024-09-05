package com.project.bunnyCare.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.user.domain.UserResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        UserResponseCode code;
        if(request.getAttribute("error") != null) {
            code = (UserResponseCode) request.getAttribute("error");
        } else {
            code = UserResponseCode.UNAUTHORIZED;
        }
        log.error("AuthenticationEntryPoint: {}", code.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper obj = new ObjectMapper();
        ApiResponse<Void> api = ApiResponse.exception(code);
        response.getWriter().println(obj.writeValueAsString(api));
    }
}
