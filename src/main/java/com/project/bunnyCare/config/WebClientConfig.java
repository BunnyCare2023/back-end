package com.project.bunnyCare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${telegram.token}")
    private String telegramToken;

    @Bean
    public WebClient telegramWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.telegram.org/bot" + telegramToken)
                .build();
    }
}
