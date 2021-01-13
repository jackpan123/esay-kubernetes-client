package com.woailqw.kubernetes.request;

import com.woailqw.kubernetes.constant.KubernetesConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackpan
 * @version v1.0 2021/1/13 14:38
 */
public class DeploymentProperties {

    public static final Integer DEFAULT_REPLICAS = 3;

    /**
     * Deployment namespace.
     */
    private String deploymentNamespace;

    /**
     * Software.
     */
    private String software;

    /**
     * Nginx version.
     */
    private String version;

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
    private Integer containerPort;

    /**
     * Replicas.
     */
    private Integer replicas;

    private DeploymentProperties(Builder builder) {
        this.deploymentNamespace = builder.deploymentNamespace;
        this.deploymentName = builder.deploymentName;
        this.containerName = builder.containerName;
        this.containerPort = builder.containerPort;
        this.replicas = builder.replicas;
        this.labels = builder.labels;
        this.software = builder.software;
        this.version = builder.version;
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
     * Gets software.
     *
     * @return Value of software.
     */
    public String getSoftware() {
        return this.software + ":" + this.version;
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
     * Gets deploymentName.
     *
     * @return Value of deploymentName.
     */
    public String getDeploymentName() {
        return this.deploymentName;
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
     * Gets containerName.
     *
     * @return Value of containerName.
     */
    public String getContainerName() {
        return this.containerName;
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
     * Gets replicas.
     *
     * @return Value of replicas.
     */
    public Integer getReplicas() {
        return this.replicas;
    }

    public static class Builder {

        /**
         * Deployment namespace.
         */
        private String deploymentNamespace =
                KubernetesConfiguration.DEFAULT_NAMESPACE;

        /**
         * Software.
         */
        private String software;

        /**
         * Nginx version.
         */
        private String version;

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
        private String containerName = null;

        /**
         * Container port.
         */
        private Integer containerPort;

        /**
         * Replicas.
         */
        private Integer replicas = DEFAULT_REPLICAS;

        public Builder(String deploymentName, String software, String version,
                       String containerName, Integer containerPort) {
            this.deploymentName = deploymentName;
            this.software = software;
            this.version = version;
            this.containerName = containerName;
            this.containerPort = containerPort;
            labels = new HashMap<>(1);
            labels.put("app", "defaultLabels");
        }

        public Builder labels(Map<String, String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder labels(String label) {
            this.labels.put("app", label);
            return this;
        }

        public Builder deploymentNamespace(String deploymentNamespace) {
            this.deploymentNamespace = deploymentNamespace;
            return this;
        }

        public Builder replicas(Integer replicas) {
            this.replicas = replicas;
            return this;
        }

        public DeploymentProperties build() {
            return new DeploymentProperties(this);
        }

    }
}
