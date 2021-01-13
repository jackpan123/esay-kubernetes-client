package com.woailqw.kubernetes;

import com.woailqw.kubernetes.request.DeploymentProperties;
import com.woailqw.kubernetes.request.NodePortServiceProperties;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Service;

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

    V1Deployment minimizeCreateSStatefulDeployment(String namespace, DeploymentProperties properties)
            throws ApiException;

    V1Service minimizeCreateService(String namespace, NodePortServiceProperties properties)
            throws ApiException;
}