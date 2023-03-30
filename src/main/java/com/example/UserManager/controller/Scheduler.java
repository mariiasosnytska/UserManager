package com.example.UserManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final UserController userController;

    @Scheduled(fixedRate = 20000, initialDelay = 20000)
    public void scheduledMethod() {
        scheduler.schedule(() -> userController.DeactivateUsers(), 0, TimeUnit.SECONDS);
    }
}
