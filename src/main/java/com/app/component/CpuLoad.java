package com.app.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuLoad extends Thread  {
    private final double load;

    private final long duration;

    public CpuLoad(String name, double load, long duration) {
        super(name);
        this.load = load;
        this.duration = duration;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            while(System.currentTimeMillis() - startTime < duration) {
                if(System.currentTimeMillis() % 100 == 0) {
                    Thread.sleep((long) Math.floor((1 - load) * 100));
                }
            }
        } catch(InterruptedException e) {
            log.error("CpuLoad.run() error");
        }
        log.info("{} : cpuLoad is done", this.getName());
    }
}
