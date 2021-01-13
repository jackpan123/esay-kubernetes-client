package com.jackpan.kubernetes.constant;

/**
 * Contain software type and version, like(type: nginx
 * version 1.14.2.
 *
 * @author Jack Pan
 * @version 1.00 2020-08-13
 */
public final class SoftwareType {

    /**
     * Constructor.
     */
    private SoftwareType() {}

    /**
     * Default nginx type.
     */
    public static final String NGINX = "nginx";

    /**
     * Default nginx version.
     */
    public static final String DEFAULT_NGINX_VERSION = "1.14.2";

    /**
     * Default nginx port.
     */
    public static final Integer DEFAULT_NGINX_PORT = 80;

    /**
     * Default replicas.
     */
    public static final Integer DEFAULT_REPLICAS = 1;
}
