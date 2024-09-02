package com.project.bunnyCare.common.jwt;

import com.project.bunnyCare.user.domain.User;
import com.project.bunnyCare.user.domain.UserResponseCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenUtil implements InitializingBean {

    private final String secret;
    private final int accessTokenHour;
    private final int refreshTokenHour;
    private static String AUTHORITIES_KEY = "auth";
    private Key key;

    TokenUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access.hour}") int accessTokenHour,
            @Value("${jwt.refresh.hour}") int refreshTokenHour
    ) {
        this.secret = secret;
        this.accessTokenHour = accessTokenHour;
        this.refreshTokenHour = refreshTokenHour;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String issueAccessToken(User user) {
        Date now = new Date();
        Date expiredAt = Date.from(LocalDateTime.now().plusHours(accessTokenHour).atZone(ZoneId.systemDefault()).toInstant());

        String authorities = new CustomUserDetails(user).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuer("bunnyCare")
                .setIssuedAt(now)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(expiredAt)
                .signWith(key)
                .compact();
    }

    public String issueRefreshToken(User user) {
        Date now = new Date();
        Date expiredAt = Date.from(LocalDateTime.now().plusHours(refreshTokenHour).atZone(ZoneId.systemDefault()).toInstant());

        String authorities = new CustomUserDetails(user).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuer("bunnyCare")
                .setIssuedAt(now)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(expiredAt)
                .signWith(key)
                .compact();
    }

    public void validateToken(String token, HttpServletRequest request) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        }
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("error", UserResponseCode.MAL_FORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            request.setAttribute("error", UserResponseCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            request.setAttribute("error", UserResponseCode.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            request.setAttribute("error", UserResponseCode.UNAUTHORIZED);
        }
    }

    public String parseBearerToken(HttpServletRequest request){
        try{
            String bearerToken = request.getHeader("Authorization");
            System.out.println(bearerToken);
            if(bearerToken.startsWith("Bearer")){
                return bearerToken.substring(7);
            }
        }catch (NullPointerException e){
            request.setAttribute("error", "jwt 토큰이 존재하지 않습니다.");
        }
        return "No JWT";
    }

    public Authentication getAuthentication(String token){

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

}
