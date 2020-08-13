package com.woailqw.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Kubernetes easy client Test.
 *
 * @author Jack Pan
 * @version 1.00 2020-08-13
 */
public final class KubernetesEasyClientTest {

    /**
     * Test kube config path.
     */
    private String kubeConfigPath = "src/main/resources/config";

    /**
     * Test deployment yaml file.
     */
    private String deploymentYaml = "src/main/resources/deployment.yaml";

    /**
     * Connect client.
     *
     * @throws ApiException If something goes wrong.
     * @throws IOException  If something goes wrong.
     */
    @Test
    public void connectClient() throws ApiException, IOException {
        // loading the out-of-cluster config, a kubeconfig from file-system
        ApiClient client =
            ClientBuilder.kubeconfig(
                KubeConfig.loadKubeConfig(new FileReader(this.kubeConfigPath)))
                .build();
        Assert.assertNotNull(client);
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);
        // the CoreV1Api loads default api-client from global configuration.
        CoreV1Api api = new CoreV1Api();
        // invokes the CoreV1Api client
        V1PodList list =
            api.listPodForAllNamespaces(null, null, null,
                null, null, null,
                null, null, null);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.getItems().size() > 0);

    }

    /**
     * Deployment list.
     *
     * @throws ApiException If something goes wrong.
     * @throws IOException  If something goes wrong.
     */
    @Test
    public void deploymentList() throws ApiException, IOException {
        // loading the out-of-cluster config, a kubeconfig from file-system
        ApiClient client =
            ClientBuilder.kubeconfig(
                KubeConfig.loadKubeConfig(new FileReader(this.kubeConfigPath)))
                .build();
        AppsV1Api appsApi = new AppsV1Api(client);
        V1DeploymentList deploymentList = appsApi
            .listDeploymentForAllNamespaces(null,
                null, null, null, null,
                null, null, null, null);
        Assert.assertNotNull(deploymentList);
    }

    /**
     * Print config context.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void printKubeConfigContext() throws IOException {
        org.yaml.snakeyaml.Yaml yaml =
            new org.yaml.snakeyaml.Yaml(new SafeConstructor());
        Object yamlString = yaml.load(new FileReader(this.kubeConfigPath));
        String dump = yaml.dump(yamlString);
        Assert.assertNotNull(dump);
    }

}
