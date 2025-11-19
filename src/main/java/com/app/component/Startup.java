package com.app.component;

import com.app.service.DefaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Startup implements ApplicationListener<ContextRefreshedEvent> {
    private final DefaultService defaultService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            log.info("[System] App is initializing");
            Thread.sleep(5 * 1000);
            log.info("[System] Database is connecting");
            Thread.sleep(5 * 1000);
            log.info("[System] Database is connected");
            Thread.sleep(5 * 1000);
            log.info("[System] App is starting");
            Thread.sleep(5 * 1000);
            log.info("[System] App is started");
            Thread.sleep(2 * 1000);
            defaultService.setAppLive(true);
            Thread.sleep(5 * 1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5 * 1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5 * 1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5 * 1000);
            log.info("[System] Data loading is completed");
            Thread.sleep(2 * 1000);
            defaultService.setAppReady(true);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
