package com.project.bunnyCare.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogKeyUtil {
    // pk 를생성해서 저장해야함.
    private static final ThreadLocal<String> logKeyThreadLocal = new ThreadLocal<>();

    public static void createLogKey() {
        String uuid = UUID.randomUUID().toString();
        logKeyThreadLocal.set(uuid);
    }

    public static String getLogKey() {
        return logKeyThreadLocal.get();
    }

    public static void removeLogKey() {
        logKeyThreadLocal.remove();
    }
}
