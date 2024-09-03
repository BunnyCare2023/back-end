package com.project.bunnyCare.config;

import com.project.bunnyCare.common.jwt.CustomAccessDeniedHandler;
import com.project.bunnyCare.common.jwt.CustomAuthenticationEntryPoint;
import com.project.bunnyCare.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(
                        ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )

                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/actuator/**","/api/auth","/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
