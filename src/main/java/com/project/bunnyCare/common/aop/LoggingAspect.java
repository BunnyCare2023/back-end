package com.project.bunnyCare.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.project.bunnyCare..interfaces.*ApiController.*(..))")
    private void apiControllerPointcut(){}

    @Before("apiControllerPointcut()")
    public void doLog(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Extract endpoint, HTTP method, and IP address
            String endpoint = request.getRequestURI();
            String httpMethod = request.getMethod();
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            LocalDateTime timestamp = LocalDateTime.now();

            // Extract method information
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            // Log the information
            log.info("Request Info - Timestamp: {}, Endpoint: {}, HttpMethod: {}, IpAddress: {}, UserAgent: {}, Class: {}, Method: {}",
                    timestamp, endpoint, httpMethod, ip, userAgent, className, methodName);
        }
    }
}
