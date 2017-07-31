package io.sphere.sdk.products;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * PublishScope.
 */
public enum PublishScope {

    /* ALL is the scope by default to allow to publish all changes */
    ALL,

    /* PRICES allow to publish only the prices */
    PRICES;

    public String toScopeString() {
        return capitalize(name().toLowerCase());
    }

    public static PublishScope ofScopeString(final String scope) {
        return valueOf(scope.toUpperCase());
    }

}
