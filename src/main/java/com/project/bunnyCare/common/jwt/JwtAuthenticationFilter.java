package com.project.bunnyCare.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenProvider;

    String[] noAuthenticationUrlList = {"/actuator","/api/v1/auth"};
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!noAuthenticate(request)) {
            try {
                String token = tokenProvider.parseBearerToken(request);
                // 토큰 검증하기 JWT이므로 인가 서버에 요청하지 않아도됨
                tokenProvider.validateToken(token, request);
                // setContext 에 인증객체 저장하기.
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (Exception e) {
            }
            //엑세스 토큰

        }
        filterChain.doFilter(request,response);
    }

    private boolean noAuthenticate(HttpServletRequest request) {

        String requestUri = request.getRequestURI();
        if(requestUri.equals("/")) return true;

        for(String uri: noAuthenticationUrlList){
            if(requestUri.contains(uri))
                return true;
        }

        return false;
    }
}
