package com.jackpan.kubernetes.request;

import com.sun.istack.internal.NotNull;
import com.jackpan.kubernetes.constant.KubernetesConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackpan
 * @version v1.0 2021/1/13 14:38
 */
public class DeploymentProperties {

    /**
     * Kubernetes namespace.
     */
    private String namespace;

    /**
     * Deployment name.
     */
    private String name;

    /**
     * Software.
     */
    private String software;

    /**
     * Nginx version.
     */
    private String version;

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
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.containerName = builder.containerName;
        this.containerPort = builder.containerPort;
        this.replicas = builder.replicas;
        this.labels = builder.labels;
        this.software = builder.imageName;
        this.version = builder.version;
    }

    /**
     * Gets namespace.
     *
     * @return Value of namespace.
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets software.
     *
     * @return Value of software.
     */
    public String image() {
        return version == null ? this.software :
                this.software + ":" + this.version;
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

        private static final Integer DEFAULT_REPLICAS = 1;

        private static final String DEFAULT_LABEL_KEY = "app";

        /**
         * Namespace.
         */
        private String namespace =
                KubernetesConfiguration.DEFAULT_NAMESPACE;

        /**
         * Software.
         */
        @NotNull
        private String imageName;

        /**
         * Nginx version.
         */
        private String version;

        /**
         * Deployment name.
         */
        @NotNull
        private String name;

        /**
         * Labels.
         */
        private Map<String, String> labels = new HashMap<>();

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
        private Integer replicas = DEFAULT_REPLICAS;

        public Builder(String name, String imageName, String version) {
            this.name = name;
            this.imageName = imageName;
            this.version = version;
            labels.put(DEFAULT_LABEL_KEY, name);
            this.containerName = name;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder labels(Map<String, String> labels) {
            this.labels.putAll(labels);
            return this;
        }

        public Builder labels(String key, String value) {
            this.labels.put(key, value);
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
