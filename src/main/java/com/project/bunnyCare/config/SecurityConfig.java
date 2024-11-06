package com.project.bunnyCare.config;

import com.project.bunnyCare.common.jwt.CustomAccessDeniedHandler;
import com.project.bunnyCare.common.jwt.CustomAuthenticationEntryPoint;
import com.project.bunnyCare.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${api.version}")
    private String version;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String authUrl = "/api/"+version+"/auth/**";
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(
                        ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )

                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/actuator/**",authUrl,"/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/news").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/news/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/news/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/news/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/feedbacks/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/v1/hospitals").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }
}
