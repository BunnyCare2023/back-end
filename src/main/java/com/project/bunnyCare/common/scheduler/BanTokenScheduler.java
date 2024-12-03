package com.project.bunnyCare.common.scheduler;

import com.project.bunnyCare.common.infrastructure.TelegramNotificationService;
import com.project.bunnyCare.token.application.BanTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class BanTokenScheduler {

    private final BanTokenService banTokenService;
    private final TelegramNotificationService telegramNotificationService;

    @Scheduled(cron = "0 0 3 * * *")
    public void deleteBanedTokenBeforeNow(){
        LocalDate now = LocalDate.now();
        Integer total = banTokenService.deleteTokensBefore(now);
        log.info("Delete Baned RefreshToken Total: {}", total);
        telegramNotificationService.sendDeleteBanedTokenResultMessage(total);
    }
}
