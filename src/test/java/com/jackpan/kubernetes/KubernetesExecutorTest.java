package com.jackpan.kubernetes;

import io.kubernetes.client.openapi.ApiException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jackpan
 * @version v1.0 2021/1/14 14:44
 */
public class KubernetesExecutorTest {

    /**
     * Test kube config path.
     */
    private String kubeConfigPath = "src/main/resources/config";

    //@Test
    public void createConfigMapTest() throws IOException, ApiException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(this.kubeConfigPath);

        KubernetesExecutor executor = new SafeKubernetesExecutor(client);
        Map<String, String> map = new HashMap<>();
        map.put("special.how", "jack");
//        FileInputS tream inputStream = new FileInputStream(file);
//        IOUtils.toByteArray();
        executor.createConfigMap("jack-config", "default", map);
    }

    //@Test
    public void createConfigMapSingleStreamTest() throws IOException, ApiException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(this.kubeConfigPath);

        KubernetesExecutor executor = new SafeKubernetesExecutor(client);
        InputStream inputStream = new FileInputStream(new File("/Users/jackpan/JackPanDocuments/temporary/nginx-configmap-test/nginx.conf"));
        executor.createConfigMapWithInputStream("nginx-config", "default", "nginx.conf", inputStream);

    }

    //@Test
    public void createConfigMapStreamListTest() throws IOException, ApiException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(this.kubeConfigPath);

        KubernetesExecutor executor = new SafeKubernetesExecutor(client);
        try (
            InputStream nginxConf = new FileInputStream(new File("/Users/jackpan/JackPanDocuments/temporary/nginx-configmap-test/nginx.conf"));
            InputStream defaultConf = new FileInputStream(new File("/Users/jackpan/JackPanDocuments/temporary/nginx-configmap-test/default.conf"))
        ) {
            Map<String, InputStream> map = new HashMap<>();
            map.put("nginx.conf", nginxConf);
            map.put("default.conf", defaultConf);
            executor.createConfigMapWithInputStream("nginx-config1", "default", map);
        }
    }
}
