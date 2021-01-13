package com.woailqw.kubernetes;

import com.woailqw.kubernetes.request.NginxProperties;
import com.woailqw.kubernetes.request.NodePortServiceProperties;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.woailqw.kubernetes.constant.KubernetesConfiguration.*;

/**
 * Kubernetes manager.
 *
 * @author Jack Pan
 * @version  1.04 2020-07-31
 */
public final class KubernetesEasyClient {

    /**
     * Kubernetes API client.
     */
    private ApiClient apiClient;

    /**
     * About kubernetes config.
     */
    private KubeConfig kubeConfig;

    /**
     * Apps V1 api.
     */
    private AppsV1Api appsApi;

    /**
     * Core V1 api.
     */
    private CoreV1Api coreApi;

    private static final String DEFAULT_PRETTY = "true";

    /**
     * Build KubernetesEasyClient through config file.
     *
     * @param absoluteFilePath Absolute file path of config file.
     * @return KubernetesEasyClient object.
     * @throws IOException If something goes wrong.
     */
    public static KubernetesEasyClient buildClient(
            final String absoluteFilePath) throws IOException {
        return new KubernetesEasyClient(absoluteFilePath);
    }

    /**
     * Build KubernetesEasyClient through config file.
     *
     * @param configFile Config file.
     * @return KubernetesEasyClient object.
     * @throws IOException If something goes wrong.
     */
    public static KubernetesEasyClient buildClient(
            final File configFile) throws IOException {
        return new KubernetesEasyClient(configFile);
    }

    /**
     * Build KubernetesEasyClient through KubeConfig.
     *
     * @param config KubeConfig properties.
     * @return KubernetesEasyClient object.
     * @throws IOException If something goes wrong.
     */
    public static KubernetesEasyClient buildClient(
            final KubeConfig config) throws IOException {
        return new KubernetesEasyClient(config);
    }

    /**
     * KubernetesEasyClient constructor through config file.
     *
     * @param absoluteFilePath Absolute file path of config file.
     * @throws IOException If something goes wrong.
     */
    private KubernetesEasyClient(
            final String absoluteFilePath) throws IOException {
        this(KubeConfig.loadKubeConfig(new FileReader(absoluteFilePath)));
    }

    /**
     * KubernetesEasyClient constructor through config file.
     *
     * @param configFile Kubernetes config file.
     * @throws IOException If something goes wrong.
     */
    private KubernetesEasyClient(final File configFile) throws IOException {
        this(KubeConfig.loadKubeConfig(new FileReader(configFile)));
    }

    /**
     * KubernetesEasyClient constructor through KubeConfig.
     *
     * @param config KubeConfig properties.
     * @throws IOException If something goes wrong.
     */
    private KubernetesEasyClient(final KubeConfig config) throws IOException {
        // loading the out-of-cluster config, a kubeconfig from file-system
        this.kubeConfig = config;
        this.apiClient =  ClientBuilder.kubeconfig(config).build();
        this.appsApi = new AppsV1Api(this.apiClient);
        this.coreApi = new CoreV1Api(this.apiClient);

    }

    /**
     * Return api client.
     *
     * @return Api client.
     */
    public ApiClient getApiClient() {
        return this.apiClient;
    }

    /**
     * Return kube config.
     *
     * @return KubeConfig.
     */
    public KubeConfig getKubeConfig() {
        return this.kubeConfig;
    }

    /**
     * Return apps api.
     *
     * @return Apps v1 api.
     */
    public AppsV1Api getAppsApi() {
        return this.appsApi;
    }

    /**
     * Return core api.
     *
     * @return Core api.
     */
    public CoreV1Api getCoreApi() {
        return this.coreApi;
    }

    /**
     * Load a Kubernetes config from a yaml string format.
     *
     * @param yamlString Yaml string.
     * @return Kubeconfig object.
     */
    public static KubeConfig loadKubeConfig(final String yamlString) {
        Object config = YAML.load(yamlString);
        Map<String, Object> configMap = (Map<String, Object>) config;

        String currentContext =
                (String) configMap.get("current-context");
        ArrayList<Object> contexts =
                (ArrayList<Object>) configMap.get("contexts");
        ArrayList<Object> clusters =
                (ArrayList<Object>) configMap.get("clusters");
        ArrayList<Object> users =
                (ArrayList<Object>) configMap.get("users");
        Object preferences = configMap.get("preferences");

        KubeConfig kubeConfig = new KubeConfig(contexts, clusters, users);
        kubeConfig.setContext(currentContext);
        kubeConfig.setPreferences(preferences);

        return kubeConfig;
    }

    /**
     * Convert yaml file to string.
     *
     * @param input File reader.
     * @return String form of yaml file.
     */
    public static String convertYamlFileToString(final Reader input) {
        Object yaml = YAML.load(input);
        return YAML.dump(yaml);
    }

    /**
     * Create kubernetes deployment quickly.
     * 快速创建K8S无状态服务.
     *
     * @param nginxProperties Nginx Properties.
     * @return Create result info about create deployment.
     * @throws ApiException When something gone wrong.
     */
    @Deprecated
    public V1Deployment createSoftwareDeployment(
            final NginxProperties nginxProperties) throws ApiException {
        V1Deployment deployment = new V1DeploymentBuilder()
                .withApiVersion(APP_VERSION)
                .withKind(DEPLOYMENT_KIND)
                .withNewMetadata()
                .withName(nginxProperties.getDeploymentName())
                .endMetadata()
                .withNewSpec()
                .withNewSelector()
                .withMatchLabels(nginxProperties.getLabels())
                .endSelector()
                .withReplicas(nginxProperties.getReplicas())
                .withNewTemplate()
                .withNewMetadata()
                .withLabels(nginxProperties.getLabels())
                .endMetadata()
                .withNewSpec()
                .withContainers(new V1ContainerBuilder()
                        .withName(nginxProperties.getContainerName())
                        .withImage(nginxProperties.image())
                        .withPorts(new V1ContainerPortBuilder()
                                .withContainerPort(nginxProperties.getContainerPort())
                                .build()).build())
                .endSpec().endTemplate().endSpec().build();

        return this.appsApi
                .createNamespacedDeployment(
                        nginxProperties.getDeploymentNamespace(),
                        deployment, null, null, null);

    }

    /**
     * Access full info about kubernetes cluster (contains image list),
     * 获取Kubernetes集群的整体信息 (包含所在镜像).
     *
     * @return Node list.
     * @throws ApiException When something gone wrong.
     */
    @Deprecated
    public List<V1Node> getNodeInfo() throws ApiException {
        return this.coreApi.listNode(null, null, null,
                null, null, null,
                null, null, null).getItems();
    }

    /**
     * Get kubernetes deployment list.
     *
     * @return Deployment list.
     * @throws ApiException
     */
    @Deprecated
    public List<V1Deployment> getDeploymentList() throws ApiException {
        V1DeploymentList deploymentList = this.appsApi
                .listDeploymentForAllNamespaces(null,
                        null, null, null, null,
                        null, null, null, null);

        return deploymentList.getItems();
    }

    /**
     * Create service by NodePort type.
     *
     * @param properties Need property.
     * @return Service info.
     * @throws ApiException If something goes wrong.
     */
    @Deprecated
    public V1Service createServiceByNodePort(NodePortServiceProperties properties)
            throws ApiException {

        Map<String, String> label = new HashMap<>(1);
        label.put("app", properties.getAppLabel());

        V1ServicePortBuilder v1ServicePortBuilder = new V1ServicePortBuilder()
                .withPort(properties.getServicePort())
                .withNewTargetPort(properties.getServiceTargetPort());

        if (properties.getNodePort() != -1) {
            v1ServicePortBuilder.withNodePort(properties.getNodePort());
        }

        V1Service service = new V1ServiceBuilder().withApiVersion(SERVICE_VERSION)
                .withKind(SERVICE_KIND)
                .withNewMetadata()
                .withName(properties.getServiceName())
                .endMetadata()
                .withNewSpec().withType(NODE_PORT)
                .withSelector(label)
                .withPorts(v1ServicePortBuilder.build())
                .endSpec().build();
        V1Service namespacedService = this.coreApi
                .createNamespacedService(properties.getServiceNamespace(), service,
                        DEFAULT_PRETTY, null, null);

        return namespacedService;
    }


    /**
     * Delete service by name and namespace.
     *
     * @param name Service name.
     * @param namespace Namespace.
     * @throws ApiException If something goes wrong.
     */
    @Deprecated
    public V1Status deleteService(String name, String namespace) throws ApiException {
        return this.coreApi.deleteNamespacedService(name, namespace, DEFAULT_PRETTY, null,
                0, null, null, null);
    }

    /**
     * Delete service by name and namespace.
     *
     * @param name Service name.
     * @param namespace Namespace.
     * @throws ApiException If something goes wrong.
     */
    @Deprecated
    public V1Status deleteDeployment(String name, String namespace) throws ApiException {
        return this.appsApi.deleteNamespacedDeployment(name, namespace, DEFAULT_PRETTY, null,
                0, null, null, null);
    }
}
