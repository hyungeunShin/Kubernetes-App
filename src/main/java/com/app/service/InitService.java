package com.app.service;

import com.app.domain.DatasourceProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitService {
    private final DatasourceProperties datasourceProperties;

    @Value(value = "${postgresql.filepath}")
    private String postgresqlFilepath;

    @PostConstruct
    public void datasourceSecretLoad() {
        Yaml y = new Yaml();
        Reader yamlFile = null;
        try {
            yamlFile = new FileReader(postgresqlFilepath);
        } catch(FileNotFoundException e) {
            log.error("DefaultService.datasourceSecretLoad() error");
        }

        if(yamlFile != null) {
            Map<String, Object> yamlMaps = y.load(yamlFile);

            datasourceProperties.setDriverClassName(yamlMaps.get("driver-class-name").toString());
            datasourceProperties.setUrl(yamlMaps.get("url").toString());
            datasourceProperties.setUsername(yamlMaps.get("username").toString());
            datasourceProperties.setPassword(yamlMaps.get("password").toString());
        }
    }
}
