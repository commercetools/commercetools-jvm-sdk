package io.sphere.sdk.products;

import io.sphere.sdk.models.SphereEnumeration;

/**
 * PublishScope.
 */
public enum PublishScope implements SphereEnumeration {

    /* ALL is the scope by default to allow to publish all changes */
    ALL,

    /* PRICES allow to publish only the prices */
    PRICES;

}
