package com.app.component;

import com.app.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleTasks {
    private final InitService InitService ;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        InitService .datasourceSecretLoad();
    }
}
