package com.jackpan.kubernetes;

import com.jackpan.kubernetes.request.DeploymentProperties;
import io.kubernetes.client.openapi.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jackpan.kubernetes.constant.KubernetesConfiguration.*;

/**
 * @author jackpan
 * @version v1.0 2021/1/13 14:32
 */
public class KubernetesExecutorFactory {


    @Deprecated
    public static V1Deployment buildNginxDeploymentWithConfigMap
    (DeploymentProperties properties,
     String configMapName, List<String> configFileList) {
        return null;
    }


    /**
     *
     * @param properties Properties file.
     * @param configMapPairs
     * @return
     */
    public static V1Deployment buildDeploymentWithConfigMap(DeploymentProperties properties, Map<String, Map<String, String>> configMapPairs) {
        if (configMapPairs.isEmpty()) {
            throw new IllegalArgumentException("configMapPairs elements must greater than one!");
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
        List<V1VolumeMount> mountFileList = new ArrayList<>();

        List<V1Volume> v1Volumes = new ArrayList<>();

        configMapPairs.forEach((configMapName, entity) -> {
            List<V1KeyToPath> keyToPathList = new ArrayList<>();
            V1VolumeBuilder v1VolumeBuilder = new V1VolumeBuilder();

            entity.forEach((absolutePath, configName) -> {
                mountFileList.add(new V1VolumeMountBuilder()
                        .withName(configMapName)
                        .withMountPath(absolutePath)
                        .withSubPath(configName)
                        .withReadOnly(false).build());

                keyToPathList.add(new V1KeyToPathBuilder()
                        .withKey(configName)
                        .withPath(configName).build());
            });

            v1VolumeBuilder.withName(configMapName).withConfigMap(
                    new V1ConfigMapVolumeSourceBuilder()
                            .withName(configMapName)
                            .withItems(keyToPathList)
                            .build());

            v1Volumes.add(v1VolumeBuilder.build());
        });

        v1ContainerBuilder.withVolumeMounts(mountFileList);

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
                .withVolumes(v1Volumes)
                .endSpec().endTemplate().endSpec();

        System.out.println(builder.build().toString());

        return builder.build();
    }
}
