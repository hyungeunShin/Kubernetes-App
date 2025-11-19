package com.app.component;

import com.app.service.DefaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleTasks {
    private final DefaultService defaultService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        defaultService.datasourceSecretLoad();
    }
}
