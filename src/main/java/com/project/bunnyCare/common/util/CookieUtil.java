package com.project.bunnyCare.common.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int age){
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge((long) age *60*60)
                .build();
        log.debug("set cookie: {}", cookie.toString());

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void removeCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(refreshToken, "")
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
        log.info("remove cookie: {}", cookie.toString());

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
