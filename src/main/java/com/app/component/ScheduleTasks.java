package com.app.component;

import com.app.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTasks {
    @Autowired
    private DefaultService defaultService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        defaultService.datasourceSecretLoad();
    }
}
