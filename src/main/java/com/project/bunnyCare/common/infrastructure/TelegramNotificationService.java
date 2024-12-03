package com.project.bunnyCare.common.infrastructure;

import com.project.bunnyCare.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationService {

    private final WebClient webClient;

    @Value("${telegram.chat-id}")
    private String telegramChatId;

    @Value("${telegram.info-chat-id}")
    private String infoChatId;

    @Async
    public void sendRegisterMessage(UserEntity user){
        // JSON 데이터 생성
        String message = String.format(
                "*회원가입 성공 알림*\n\n- 이름: %s\n- 이메일: %s\n- 소셜 타입: %s",
                user.getName(), user.getEmail(), user.getSocialType().getValue()
        );
        Map<String, String> requestBody = Map.of(
                "chat_id", telegramChatId,
                "text", message
        );

        webClient.post()
                .uri("/sendMessage")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class) // 응답 처리
                .doOnError(e -> log.error("Failed to send Telegram message: {}", e.getMessage()))
                .subscribe();
    }

    @Async
    public void sendDeleteMessage(UserEntity user, String deleteReason){
        String message = String.format(
                "*회원탈퇴 성공 알림*\n\n -이름: %s\n- 이메일: %s\n- 소셜타입: %s\n- 탈퇴 사유: %s",
                user.getName(), user.getEmail(), user.getSocialType().getValue(), deleteReason
        );

        Map<String, String> requestBody = Map.of(
                "chat_id", telegramChatId,
                "text", message
        );

        webClient.post().uri("/sendMessage")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Failed to send Telegram message: {}", e.getMessage()))
                .subscribe();
    }

    @Async
    public void sendDeleteBanedTokenResultMessage(Integer total){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String date = now.format(formatter);

        String message = String.format(
                "*밴 토큰 삭제 스케쥴 알림*\n\n -시간: %s\n- 총 개수: %d",
                date, total
        );

        Map<String, String> requestBody = Map.of(
                "chat_id", infoChatId,
                "text", message
        );

        webClient.post().uri("/sendMessage")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Failed to send Telegram message: {}", e.getMessage()))
                .subscribe();
    }
}
