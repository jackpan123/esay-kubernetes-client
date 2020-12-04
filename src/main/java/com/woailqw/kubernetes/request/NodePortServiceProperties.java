package com.woailqw.kubernetes.request;


import com.woailqw.kubernetes.constant.KubernetesConfiguration;

/**
 * Properties for node port service creation.
 *
 * @author jackpan
 * @version v1.0 2020/11/27 16:36
 */
public class NodePortServiceProperties {

    private String serviceNamespace;

    private String serviceName;

    private String appLabel;

    private Integer servicePort;

    private Integer serviceTargetPort;

    private Integer nodePort;

    private NodePortServiceProperties(Builder builder) {
        this.serviceNamespace = builder.serviceNamespace;
        this.serviceName = builder.serviceName;
        this.appLabel = builder.appLabel;
        this.servicePort = builder.servicePort;
        this.serviceTargetPort = builder.serviceTargetPort;
        this.nodePort = builder.nodePort;
    }

    public static class Builder {

        private String serviceNamespace;

        private String serviceName;

        private String appLabel;

        private Integer servicePort;

        private Integer serviceTargetPort;

        private Integer nodePort;

        public Builder(String serviceName, String appLabel,
            Integer servicePort, Integer serviceTargetPort) {
            this.serviceNamespace = KubernetesConfiguration.DEFAULT_NAMESPACE;
            this.serviceName = serviceName;
            this.appLabel = appLabel;
            this.servicePort = servicePort;
            this.serviceTargetPort = serviceTargetPort;
            this.nodePort = -1;
        }

        public Builder serviceNamespace(String val) {
            this.serviceNamespace = val;
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
     * Gets serviceNamespace.
     *
     * @return Value of serviceNamespace.
     */
    public String getServiceNamespace() {
        return this.serviceNamespace;
    }

    /**
     * Sets serviceNamespace.
     *
     * @param serviceNamespace Simple param.
     */
    public void setServiceNamespace(String serviceNamespace) {
        this.serviceNamespace = serviceNamespace;
    }

    /**
     * Gets serviceName.
     *
     * @return Value of serviceName.
     */
    public String getServiceName() {
        return this.serviceName;
    }

    /**
     * Sets serviceName.
     *
     * @param serviceName Simple param.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets appLabel.
     *
     * @return Value of appLabel.
     */
    public String getAppLabel() {
        return this.appLabel;
    }

    /**
     * Sets appLabel.
     *
     * @param appLabel Simple param.
     */
    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
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
     * Sets servicePort.
     *
     * @param servicePort Simple param.
     */
    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
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
     * Sets serviceTargetPort.
     *
     * @param serviceTargetPort Simple param.
     */
    public void setServiceTargetPort(Integer serviceTargetPort) {
        this.serviceTargetPort = serviceTargetPort;
    }

    /**
     * Gets nodePort.
     *
     * @return Value of nodePort.
     */
    public Integer getNodePort() {
        return this.nodePort;
    }

    /**
     * Sets nodePort.
     *
     * @param nodePort Simple param.
     */
    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }
}
