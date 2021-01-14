package com.jackpan.kubernetes;

import com.jackpan.kubernetes.request.DeploymentProperties;
import io.kubernetes.client.openapi.models.*;

import java.util.ArrayList;
import java.util.List;

import static com.jackpan.kubernetes.constant.KubernetesConfiguration.APP_VERSION;
import static com.jackpan.kubernetes.constant.KubernetesConfiguration.DEPLOYMENT_KIND;

/**
 * @author jackpan
 * @version v1.0 2021/1/13 14:32
 */
public class KubernetesExecutorFactory {

    private static final String NGINX_VOLUME_NAME = "nginx-conf";

    private static final String NGINX_CONF_PATH_PREFIX = "/etc/nginx/";

    public static V1Deployment buildNginxDeployemntWithConfigMap(DeploymentProperties properties,
                                                                 String configMapName, List<String> configFileList) {

        if (configFileList.isEmpty()) {
            throw new IllegalArgumentException("configFileList elements must greater than one!");
        }
        // Create port.
        V1ContainerPortBuilder v1ContainerPortBuilder = null;

        V1ContainerBuilder v1ContainerBuilder = new V1ContainerBuilder()
                .withName(properties.getContainerName())
                .withImage(properties.image());

        if (properties.getContainerPort() != null) {
            v1ContainerPortBuilder = new V1ContainerPortBuilder()
                    .withContainerPort(properties.getContainerPort());
            v1ContainerBuilder.withPorts(v1ContainerPortBuilder.build());
        }

        // Create volume mount path.
        List<V1VolumeMount> mountFileList = new ArrayList<>(configFileList.size());

        List<V1KeyToPath> keyToPathList = new ArrayList<>(configFileList.size());

        for (String configFileName : configFileList) {
            mountFileList.add(new V1VolumeMountBuilder()
                    .withName(NGINX_VOLUME_NAME)
                    .withMountPath(NGINX_CONF_PATH_PREFIX + configFileName)
                    .withSubPath(configFileName)
                    .withReadOnly(true).build());

            keyToPathList.add(new V1KeyToPathBuilder()
                    .withKey(configFileName)
                    .withPath(configFileName).build());
        }

        v1ContainerBuilder.withVolumeMounts(mountFileList);

        // Create volumes.
        V1VolumeBuilder v1VolumeBuilder = new V1VolumeBuilder();
        v1VolumeBuilder.withName(NGINX_VOLUME_NAME).withConfigMap(
                new V1ConfigMapVolumeSourceBuilder()
                        .withName(configMapName)
                        .withItems(keyToPathList)
                        .build());

        V1DeploymentBuilder builder = new V1DeploymentBuilder()
            .withApiVersion(APP_VERSION)
            .withKind(DEPLOYMENT_KIND)
            .withNewMetadata()
            .withName(properties.getName())
            .withLabels(properties.getLabels())
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
            .withVolumes(v1VolumeBuilder.build())
            .endSpec().endTemplate().endSpec();

        return builder.build();
    }
}
