package com.jackpan.kubernetes.request;


import com.jackpan.kubernetes.constant.KubernetesConfiguration;

import java.util.Map;

/**
 * Properties for node port service creation.
 *
 * @author jackpan
 * @version v1.0 2020/11/27 16:36
 */
public class NodePortServiceProperties {

    private String namespace;

    private String name;

    /**
     * Labels.
     */
    private Map<String, String> labels;

    private Integer servicePort;

    private Integer serviceTargetPort;

    private Integer nodePort;

    private NodePortServiceProperties(Builder builder) {
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.labels = builder.labels;
        this.servicePort = builder.servicePort;
        this.serviceTargetPort = builder.serviceTargetPort;
        this.nodePort = builder.nodePort;
    }

    public static class Builder {

        private String namespace;

        private String name;

        /**
         * Labels.
         */
        private Map<String, String> labels;

        private Integer servicePort;

        private Integer serviceTargetPort;

        private Integer nodePort;

        public Builder(String name, Map<String, String> labels,
            Integer servicePort, Integer serviceTargetPort) {
            this.namespace = KubernetesConfiguration.DEFAULT_NAMESPACE;
            this.name = name;
            this.labels = labels;
            this.servicePort = servicePort;
            this.serviceTargetPort = serviceTargetPort;
            this.nodePort = -1;
        }

        public Builder namespace(String val) {
            this.namespace = val;
            return this;
        }

        public Builder nodePort(Integer val) {
            this.nodePort = val;
            return this;
        }

        public NodePortServiceProperties build() {
            return new NodePortServiceProperties(this);
        }
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
     * Gets labels.
     *
     * @return Value of labels.
     */
    public Map<String, String> getLabels() {
        return this.labels;
    }

    /**
     * Gets servicePort.
     *
     * @return Value of servicePort.
     */
    public Integer getServicePort() {
        return this.servicePort;
    }

    /**
     * Gets serviceTargetPort.
     *
     * @return Value of serviceTargetPort.
     */
    public Integer getServiceTargetPort() {
        return this.serviceTargetPort;
    }

    /**
     * Gets nodePort.
     *
     * @return Value of nodePort.
     */
    public Integer getNodePort() {
        return this.nodePort;
    }
}
