package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

/**
 * Holds the names of commercetools proprietary HTTP headers.
 */
public final class SphereHttpHeaders extends Base {
    private SphereHttpHeaders() {
    }

    /**
     * Used for http requests which are deprecated on the commercetools platform.
     */
    public static final String X_DEPRECATION_NOTICE = "X-DEPRECATION-NOTICE";
}
