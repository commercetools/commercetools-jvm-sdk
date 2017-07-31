package io.sphere.sdk.products;

import io.sphere.sdk.client.SphereProjectScope;
import io.sphere.sdk.models.Base;

/**
 * PublishScope.
 */
public enum PublishScope {

    /* ALL is the scope by default to allow to publish all changes */
    ALL,

    /* PRICES allow to publish only the prices */
    PRICES;

    public String toScopeString() {
        final String lowerCase = name().toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }

    public static PublishScope ofScopeString(final String scope) {
        return valueOf(scope.toUpperCase());
    }

}
