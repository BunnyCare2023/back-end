package com.project.bunnyCare.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.user.domain.UserResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("AccessDeniedHandler key: {}", UserResponseCode.FORBIDDEN.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiResponse<Void> api = ApiResponse.exception(UserResponseCode.FORBIDDEN);
        ObjectMapper obj = new ObjectMapper();
        response.getWriter().println(obj.writeValueAsString(api));
    }
}
