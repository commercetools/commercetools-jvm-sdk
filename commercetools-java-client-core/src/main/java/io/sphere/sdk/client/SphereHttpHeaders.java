package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

/**
 * Holds the names of commercetools Composable Commerce proprietary HTTP headers.
 */
public final class SphereHttpHeaders extends Base {
    private SphereHttpHeaders() {
    }

    /**
     * Used for http requests which are deprecated on Composable Commerce.
     */
    public static final String X_DEPRECATION_NOTICE = "X-DEPRECATION-NOTICE";
}
