package com.app.service;

import com.app.domain.DatasourceProperties;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class Sprint3Service {
    private final DatasourceProperties datasourceProperties;

    public String loadDownwardApiFile(String path)  {
        StringBuilder allContents = new StringBuilder();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            List<Path> fileList = paths.filter(Files::isRegularFile).toList();

            for(Path file : fileList) {
                if(file.toString().contains("..")) {
                    continue;
                }

                allContents.append("<b>File: ").append(file).append("</b><br>");

                List<String> fileContent = Files.readAllLines(file);
                for(String line : fileContent) {
                    allContents.append(line).append("<br>");
                }
                allContents.append("---<br>");
            }
        } catch(IOException e) {
            log.error("{}", e.getMessage());
        }

        return allContents.toString();
    }

    public String getSelfPodKubeApiServer(String clusterUrl, String podName, String path) {
        log.info(clusterUrl);

        //토큰과 CA 인증서 경로 설정
        String tokenPath = path + "token";
        String caPath = path + "ca.crt";
        String namespacePath = path + "namespace";
        String responseString = "";

        try {
            //파일에서 Token 읽기
            String token = new String(Files.readAllBytes(Paths.get(tokenPath)));
            String namespace = new String(Files.readAllBytes(Paths.get(namespacePath)));

            log.info("token: {}", token);
            log.info("caPath: {}", caPath);
            log.info("namespace: {}", namespace);
            log.info("podName: {}", podName);

            //ApiClient 생성 및 설정
            ApiClient client = Config.defaultClient();
            client.setBasePath(clusterUrl);
            client.setApiKey("Bearer " + token);
            client.setSslCaCert(new java.io.FileInputStream(caPath));
            Configuration.setDefaultApiClient(client);

            //Kubernetes API 호출
            CoreV1Api api = new CoreV1Api();
            V1Pod pod = api.readNamespacedPod(podName, namespace, "true");
            responseString = Yaml.dump(pod);

            log.info("responseString: {}", responseString);
        } catch(ApiException e) {
            log.error("Status: {}", e.getCode());
            log.error("Body: {}", e.getResponseBody());
            responseString = e.getResponseBody();
        } catch(Exception e) {
            log.error("{}", e.getMessage());
        }

        return responseString;
    }
}