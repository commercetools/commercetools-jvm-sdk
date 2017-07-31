package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;

/**
 * PublishScope.
 */
public final class PublishScope extends Base {

    private final String scope;

    public PublishScope(final String scope) {
        this.scope = scope;
    }

    /* ALL is the scope by default  allow to publish only the prices */
    public final static PublishScope ALL = of("All");

    /* PRICES allow to publish only the prices */
    public final static PublishScope PRICES = of("Prices");

    private static PublishScope of(final String scope) {
        return new PublishScope(scope);
    }

    public String toScopeString() {
        return this.scope;
    }

}
