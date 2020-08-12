package com.woailqw.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Kubernetes manager.
 *
 * @author Jack Pan
 * @vresion 1.00 2020-07-31
 */
public class KubernetesEasyClient {

    /**
     * Kubernetes API client.
     */
    private ApiClient apiClient;

    /**
     * About kubernetes config.
     */
    private KubeConfig kubeConfig;

    private static final Yaml yaml = new Yaml(new SafeConstructor());

    public KubernetesEasyClient(String absoluteFilePath) throws IOException {
        this(KubeConfig.loadKubeConfig(new FileReader(absoluteFilePath)));
    }

    public KubernetesEasyClient(File configFile) throws IOException {
        this(KubeConfig.loadKubeConfig(new FileReader(configFile)));
    }

    public KubernetesEasyClient(KubeConfig config) throws IOException {
        // loading the out-of-cluster config, a kubeconfig from file-system
        this.kubeConfig = config;
        this.apiClient =  ClientBuilder.kubeconfig(config).build();

    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public KubeConfig getKubeConfig() {
        return kubeConfig;
    }

    /** Load a Kubernetes config from a yaml string format*/
    public static KubeConfig loadKubeConfig(String yamlString) {
        Object config = yaml.load(yamlString);
        Map<String, Object> configMap = (Map<String, Object>) config;

        String currentContext = (String) configMap.get("current-context");
        ArrayList<Object> contexts = (ArrayList<Object>) configMap.get("contexts");
        ArrayList<Object> clusters = (ArrayList<Object>) configMap.get("clusters");
        ArrayList<Object> users = (ArrayList<Object>) configMap.get("users");
        Object preferences = configMap.get("preferences");

        KubeConfig kubeConfig = new KubeConfig(contexts, clusters, users);
        kubeConfig.setContext(currentContext);
        kubeConfig.setPreferences(preferences);

        return kubeConfig;
    }

    public static String convertYamlFileToString(Reader input) {
        Object s = yaml.load(input);
        return yaml.dump(s);
    }
    /**
     * 快速创建K8S无状态服务
     * Create kubernetes deployment quickly.
     *
     * @param software
     * @param version
     */
    V1Deployment createSoftwareDeployment(String software, String version) {
        return null;
    }

    /**
     * 获取Kubernetes集群的整体信息 (包含所在镜像).
     * Access full info about kubernetes cluster (contains image list).
     * @return
     */
    List<V1Node> getNodeInfo() {
        return null;
    }
}
