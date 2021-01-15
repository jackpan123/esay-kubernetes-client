package com.jackpan.kubernetes;

import com.jackpan.kubernetes.request.NodePortServiceProperties;
import com.jackpan.kubernetes.request.DeploymentProperties;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1Status;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Kubernetes executor.
 * It can create deployment, service.
 * @author jackpan
 * @version v1.0 2021/1/13 14:28
 */
public interface KubernetesExecutor {

    /**
     * Create deployment.
     * @param namespace
     * @param body
     * @return
     * @throws ApiException
     */
    V1Deployment createDeployment(String namespace, V1Deployment body)
            throws ApiException;

    V1Service createService(String namespace, V1Service body)
            throws ApiException;

    V1Deployment minimizeCreateStatelessDeployment(String namespace, DeploymentProperties properties)
            throws ApiException;

    V1Deployment minimizeCreateStatefulDeployment(String namespace, DeploymentProperties properties)
            throws ApiException;

    V1Service minimizeCreateService(String namespace, NodePortServiceProperties properties)
            throws ApiException;

    V1Status deleteService(String name, String namespace) throws ApiException;

    V1Status deleteDeployment(String name, String namespace) throws ApiException;

    V1Deployment createNginxDeploymentWithConfigMap(DeploymentProperties properties,
                                                    String configMapName, List<String> configFileList) throws ApiException;

    V1ConfigMap createConfigMap(String configName, String namespace,
                                Map<String, String> configMap) throws ApiException;

    V1ConfigMap createConfigMapWithInputStream(String configName, String namespace,
                                Map<String, InputStream> configMap) throws ApiException, IOException;

    V1ConfigMap createConfigMapWithInputStream(String configName, String namespace,
                                               String key, InputStream configFile) throws ApiException, IOException;

    V1Deployment createMySQLDeploymentWithConfigMap(DeploymentProperties properties,
                                                    String configMapName, List<String> configFileList) throws ApiException;
}
