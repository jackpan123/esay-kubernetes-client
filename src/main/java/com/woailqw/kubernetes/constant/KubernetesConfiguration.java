package com.woailqw.kubernetes.constant;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Kubernetes configuration.
 *
 * @author Jack Pan
 * @version 1.00 2020-08-13
 */
public final class KubernetesConfiguration {

    /**
     * Constructor.
     */
    private KubernetesConfiguration() {}

    /**
     * Yaml operator.
     */
    public static final Yaml YAML = new Yaml(new SafeConstructor());

    /**
     * Kubernetes app version.
     */
    public static final String APP_VERSION = "apps/v1";

    /**
     * Deployment kind.
     */
    public static final String DEPLOYMENT_KIND = "Deployment";

    /**
     * Default namespace.
     */
    public static final String DEFAULT_NAMESPACE = "default";

    /**
     * Service kind.
     */
    public static final String SERVICE_KIND = "Service";

    /**
     * Node port type.
     */
    public static final String NODE_PORT = "NodePort";
}
