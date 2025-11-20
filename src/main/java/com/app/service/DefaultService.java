package com.app.service;

import com.app.component.CpuLoad;
import com.app.component.ObjectForLeak;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DefaultService {
    private Boolean isAppLive = false;

    private Boolean isAppReady = false;

    public void setAppLive(Boolean appLive) {
        isAppLive = appLive;
    }

    public void setAppReady(Boolean appReady) {
        isAppReady = appReady;
    }

    public String hostname() {
        String hostname;
        InetAddress inetAdd;
        try {
            inetAdd = InetAddress.getLocalHost();
        } catch(UnknownHostException e) {
            throw new RuntimeException(e);
        }
        hostname = inetAdd.getHostName();
        return hostname;
    }

    public Boolean probeCheck(String type) {
        if(type.equals("startup") || type.equals("liveness")) {
            log.info("[Kubernetes] {}Probe is {}-> [System] isAppLive: {}", type, isAppLive ? "Succeed" : "Failed", isAppLive);
            return isAppLive;
        } else {
            log.info("[Kubernetes] {}Probe is {}-> [System] isAppReady: {}", type, isAppReady ? "Succeed" : "Failed", isAppReady);
            return isAppReady;
        }
    }

    static List<ObjectForLeak> leak = new ArrayList<>();

    public void memoryLeak() {
        log.info("{} : memoryLeak is starting", this.hostname());
        while(true) {
            leak.add(new ObjectForLeak());
        }
    }

    public void cpuLoad(int min, int thread) {
        final long duration = (long) min * 60 * 1000;  //3분동안
        double load = 0.8;  //부하를 80%정도로 유지하도록 설정

        for(int cnt = 0; cnt < thread; cnt++) {
            String name = "Thread-" + cnt + "-" + this.hostname();
            log.info("{} : cpuLoad is starting ({} min)", this.hostname(), min);
            new CpuLoad(name + cnt, load, duration).start();
        }
    }

    public String listFiles(String path) {
        File file = new File(path);
        String[] files = file.list();

        if(files == null) {
            return "";
        }

        StringBuilder filenameList = new StringBuilder();
        for(String filename : files) {
            filenameList.insert(0, filename + " ");
        }
        return filenameList.toString();
    }

    public String createFile(String path) {
        //폴더 생성
        File filePath = new File(path);
        if(!filePath.exists()) {
            filePath.mkdirs();
        }

        //10자리 임의 문자 만들기
        StringBuilder randomStr = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            char sValue = (char) ((int) (Math.random() * 26) + 97);
            randomStr.append(sValue);
        }
        log.info("File created:{}", path);

        //문자로 파일명 생성
        String filename = path + randomStr + ".txt";
        File file = new File(filename);
        try {
            if(file.createNewFile()) {
                log.info("File created");
            } else {
                log.info("File already exists");
            }
        } catch(IOException e) {
            log.error("DefaultService.createFile() error");
        }

        return listFiles(path);
    }
}
