package com.jackpan.kubernetes;

import com.jackpan.kubernetes.request.DeploymentProperties;
import com.jackpan.kubernetes.request.NodePortServiceProperties;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Service;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
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
                .buildClient(KubernetesEasyClient.loadKubeConfig(KubernetesEasyClient.convertYamlFileToString(new FileReader(new File(kubeConfigPath)))));

        System.out.println(client.getCoreApi());
        System.out.println();
        KubernetesExecutor executor = new SafeKubernetesExecutor(client);
//        Map<String, String> map = new HashMap<>();
//        map.put("special.how", "jack");
//        executor.createConfigMap("jack-config2", "default", map);
        executor.createNamespace("jackhahah");
    }

    private SafeKubernetesExecutor connectClient() throws IOException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(KubernetesEasyClient.loadKubeConfig(KubernetesEasyClient.convertYamlFileToString(new FileReader(new File(kubeConfigPath)))));
        return new SafeKubernetesExecutor(client);
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

    //@Test
    public void createMysqlDeployment() throws ApiException, IOException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(this.kubeConfigPath);

        KubernetesExecutor executor = new SafeKubernetesExecutor(client);

        //V1Deployment mySQLDeploymentWithConfigMap = executor.createMySQLDeployment();
    }

    //@Test
    public void createService() throws ApiException, IOException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(this.kubeConfigPath);

        KubernetesExecutor executor = new SafeKubernetesExecutor(client);

        Map<String, String> labels = new HashMap<>();
        labels.put("app", "jack-mysql");
        NodePortServiceProperties properties = new NodePortServiceProperties.Builder("jackservice1",
                labels, 3306, 3306).build();
        V1Service aDefault = executor.minimizeCreateService("default", properties);
        System.out.println();
    }


    //@Test
    public void patchDeploymentTest() throws IOException, ApiException {
        KubernetesEasyClient client = KubernetesEasyClient
                .buildClient(KubernetesEasyClient.loadKubeConfig(KubernetesEasyClient.convertYamlFileToString(new FileReader(new File(kubeConfigPath)))));

        System.out.println(client.getCoreApi());
        System.out.println();
        KubernetesExecutor executor = new SafeKubernetesExecutor(client);
        DeploymentProperties build = new DeploymentProperties.Builder("jack-test12", "nginx", "1.16.1").build();
        V1Deployment v1Deployment = executor.patchDeploymentImageVersion("jackhahah", build);
    }

    //@Test
    public void createConfigMapWithFileTest() throws IOException, ApiException {
        KubernetesExecutor executor = connectClient();
        V1ConfigMap aDefault = executor.createConfigMapWithInputStream("test-tomcat-conf", "default",
                "catalina.sh", new FileInputStream(new File("/Users/jackpan/Downloads/catalina.sh")));
    }

    //@Test
    public void createTomcatDeployment() throws IOException, ApiException {
        KubernetesExecutor executor = connectClient();
        DeploymentProperties build = new DeploymentProperties.Builder("jack-test-tomcat", "tomcat", "jdk8").build();
        V1Deployment aDefault = executor.minimizeCreateStatelessDeployment("default", build);
    }

    //@Test
    public void createNginxDeployment() throws IOException, ApiException {
        KubernetesExecutor executor = connectClient();
        DeploymentProperties build = new DeploymentProperties.Builder("jack-test-nginx", "nginx", "1.14.2").build();
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> configField = new HashMap<>();
        configField.put("/etc/nginx/nginx.conf", "nginx.conf");
        configField.put("/etc/nginx/default.conf", "default.conf");
        map.put("nginx-config1", configField);
        V1Deployment v1Deployment = KubernetesExecutorFactory.buildDeploymentWithConfigMap(build, map);
        V1Deployment aDefault = executor.createDeployment("default", v1Deployment);
    }

}
