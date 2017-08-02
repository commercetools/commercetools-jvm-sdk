package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonValue;

import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * PublishScope.
 */
public enum PublishScope {

    /* ALL is the scope by default to allow to publish all changes */
    ALL,

    /* PRICES allow to publish only the prices */
    PRICES;

    @JsonValue
    public String toScopeString() {
        return capitalize(name().toLowerCase());
    }

}
