package com.woailqw.kubernetes;

import com.woailqw.kubernetes.request.DeploymentProperties;
import com.woailqw.kubernetes.request.NodePortServiceProperties;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.*;

import static com.woailqw.kubernetes.constant.KubernetesConfiguration.APP_VERSION;
import static com.woailqw.kubernetes.constant.KubernetesConfiguration.DEPLOYMENT_KIND;

/**
 * @author jackpan
 * @version v1.0 2021/1/13 14:56
 */
public class SafeKubernetesExecutor implements KubernetesExecutor {

    private final KubernetesEasyClient client;

    public SafeKubernetesExecutor(KubernetesEasyClient client) {
        this.client = client;
    }

    /**
     * Default pretty
     */
    private static final String DEFAULT_PRETTY = "true";

    /**
     * Create deployment.
     *
     * @param namespace
     * @param body
     * @return
     * @throws ApiException
     */
    @Override
    public V1Deployment createDeployment(String namespace, V1Deployment body) throws ApiException {
        return createDeploymentInternal(namespace, body);
    }

    private V1Deployment createDeploymentInternal(String namespace, V1Deployment body) throws ApiException {
        return this.client.getAppsApi()
                .createNamespacedDeployment(
                        namespace, body, DEFAULT_PRETTY, null, null);
    }

    @Override
    public V1Service createService(String namespace, V1Service body) throws ApiException {
        return createServiceInternal(namespace, body);
    }

    private V1Service createServiceInternal(String namespace, V1Service body) throws ApiException {
        return this.client.getCoreApi()
                .createNamespacedService(
                        namespace, body, DEFAULT_PRETTY, null, null);
    }

    @Override
    public V1Deployment minimizeCreateStatelessDeployment(String namespace, DeploymentProperties properties) throws ApiException {

        V1ContainerPortBuilder v1ContainerPortBuilder = null;

        V1ContainerBuilder v1ContainerBuilder = new V1ContainerBuilder()
                .withName(properties.getContainerName())
                .withImage(properties.image());

        if (properties.getContainerPort() != null) {
            v1ContainerPortBuilder = new V1ContainerPortBuilder()
                    .withContainerPort(properties.getContainerPort());
            v1ContainerBuilder.withPorts(v1ContainerPortBuilder.build());
        }

        V1DeploymentBuilder builder = new V1DeploymentBuilder()
                .withApiVersion(APP_VERSION)
                .withKind(DEPLOYMENT_KIND)
                .withNewMetadata()
                .withName(properties.getName())
                .endMetadata()
                .withNewSpec()
                .withNewSelector()
                .withMatchLabels(properties.getLabels())
                .endSelector()
                .withReplicas(properties.getReplicas())
                .withNewTemplate()
                .withNewMetadata()
                .withLabels(properties.getLabels())
                .endMetadata()
                .withNewSpec()
                .withContainers(v1ContainerBuilder.build())
                .endSpec().endTemplate().endSpec();

        return createDeploymentInternal(properties.getNamespace(), builder.build());
    }

    @Override
    public V1Deployment minimizeCreateStatefulDeployment(String namespace, DeploymentProperties properties) throws ApiException {
        return null;
    }

    @Override
    public V1Service minimizeCreateService(String namespace, NodePortServiceProperties properties) throws ApiException {
        return null;
    }
}
