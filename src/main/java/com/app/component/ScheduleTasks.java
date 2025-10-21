package com.app.component;

import com.app.service.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ScheduleTasks {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private DefaultService defaultService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        defaultService.datasourceSecretLoad();
    }
}
