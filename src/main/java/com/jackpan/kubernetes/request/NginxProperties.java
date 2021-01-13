package com.jackpan.kubernetes.request;

import com.jackpan.kubernetes.constant.KubernetesConfiguration;
import com.jackpan.kubernetes.constant.SoftwareType;
import java.util.Map;

/**
 * Create nginx properties.
 *
 * @author Jack Pan
 * @version 1.00 2020-08-13
 */
public final class NginxProperties {


    /**
     * Software.
     */
    private String software = SoftwareType.NGINX;

    /**
     * Nginx version.
     */
    private String version = SoftwareType.DEFAULT_NGINX_VERSION;

    /**
     * Deployment name.
     */
    private String deploymentName;

    /**
     * Labels.
     */
    private Map<String, String> labels;

    /**
     * Container name.
     */
    private String containerName;

    /**
     * Container port.
     */
    private Integer containerPort = SoftwareType.DEFAULT_NGINX_PORT;

    /**
     * Replicas.
     */
    private Integer replicas = SoftwareType.DEFAULT_REPLICAS;

    /**
     * Deployment namespace.
     */
    private String deploymentNamespace =
        KubernetesConfiguration.DEFAULT_NAMESPACE;


    /**
     * Constructor.
     *
     * @param deploymentName DeploymentName.
     * @param labels Labels.
     * @param containerName ContainerName.
     */
    public NginxProperties(
        final String deploymentName,
        final Map<String, String> labels,
        final String containerName) {

        this.deploymentName = deploymentName;
        this.labels = labels;
        this.containerName = containerName;
    }

    /**
     * Gets software.
     *
     * @return Value of software.
     */
    public String getSoftware() {
        return this.software;
    }

    /**
     * Sets software.
     *
     * @param software Simple param.
     */
    public void setSoftware(final String software) {
        this.software = software;
    }

    /**
     * Gets version.
     *
     * @return Value of version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets version.
     *
     * @param version Simple param.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Gets deploymentName.
     *
     * @return Value of deploymentName.
     */
    public String getDeploymentName() {
        return this.deploymentName;
    }

    /**
     * Sets deploymentName.
     *
     * @param deploymentName Simple param.
     */
    public void setDeploymentName(final String deploymentName) {
        this.deploymentName = deploymentName;
    }

    /**
     * Gets labels.
     *
     * @return Value of labels.
     */
    public Map<String, String> getLabels() {
        return this.labels;
    }

    /**
     * Sets labels.
     *
     * @param labels Simple param.
     */
    public void setLabels(final Map<String, String> labels) {
        this.labels = labels;
    }

    /**
     * Gets containerName.
     *
     * @return Value of containerName.
     */
    public String getContainerName() {
        return this.containerName;
    }

    /**
     * Sets containerName.
     *
     * @param containerName Simple param.
     */
    public void setContainerName(final String containerName) {
        this.containerName = containerName;
    }

    /**
     * Gets containerPort.
     *
     * @return Value of containerPort.
     */
    public Integer getContainerPort() {
        return this.containerPort;
    }

    /**
     * Sets containerPort.
     *
     * @param containerPort Simple param.
     */
    public void setContainerPort(final Integer containerPort) {
        this.containerPort = containerPort;
    }

    /**
     * Gets replicas.
     *
     * @return Value of replicas.
     */
    public Integer getReplicas() {
        return this.replicas;
    }

    /**
     * Sets replicas.
     *
     * @param replicas Simple param.
     */
    public void setReplicas(final Integer replicas) {
        this.replicas = replicas;
    }

    /**
     * Gets deploymentNamespace.
     *
     * @return Value of deploymentNamespace.
     */
    public String getDeploymentNamespace() {
        return this.deploymentNamespace;
    }

    /**
     * Sets deploymentNamespace.
     *
     * @param deploymentNamespace Simple param.
     */
    public void setDeploymentNamespace(final String deploymentNamespace) {
        this.deploymentNamespace = deploymentNamespace;
    }

    /**
     * Connect software and version.
     *
     * @return String.
     */
    public String image() {
        return this.software.concat(":").concat(this.version);
    }
}
