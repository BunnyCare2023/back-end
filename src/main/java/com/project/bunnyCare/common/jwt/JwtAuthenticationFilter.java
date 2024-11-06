package com.project.bunnyCare.common.jwt;

import com.project.bunnyCare.common.exception.JwtAuthenticationException;
import com.project.bunnyCare.user.domain.UserResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenProvider;

    String[] noAuthenticationUrlList = {"/actuator","/api/v1/auth", "/favicon.ico"};
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!noAuthenticate(request)) {
            String token = tokenProvider.parseBearerToken(request);
            try {
                if(token != null) {
                    tokenProvider.validateToken(token);
                    // setContext 에 인증객체 저장하기.
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    throw new JwtAuthenticationException("Token is not valid Null");
                }
            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                request.setAttribute("error", UserResponseCode.MAL_FORMED_TOKEN);
                throw new JwtAuthenticationException("Token is not valid", e);
            } catch (ExpiredJwtException e) {
                request.setAttribute("error", UserResponseCode.EXPIRED_TOKEN);
                throw new JwtAuthenticationException("Token is expired", e);
            } catch (UnsupportedJwtException e) {
                request.setAttribute("error", UserResponseCode.UNSUPPORTED_TOKEN);
                throw new JwtAuthenticationException("Token is not supported", e);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", UserResponseCode.IllegalArgumentException);
                throw new JwtAuthenticationException("JWT token is null or empty", e);
            }

        }
        filterChain.doFilter(request,response);
    }

    private boolean noAuthenticate(HttpServletRequest request) {

        String requestUri = request.getRequestURI();
        if(requestUri.equals("/")) return true;
        if(requestUri.equals("/api/v1/auth/delete")) return false;

        for(String uri: noAuthenticationUrlList){
            if(requestUri.contains(uri))
                return true;
        }

        return false;
    }
}
