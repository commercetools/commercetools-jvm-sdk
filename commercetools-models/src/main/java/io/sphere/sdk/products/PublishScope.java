package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * PublishScope.
 */
public enum PublishScope implements SphereEnumeration {

    /* ALL is the scope by default to allow to publish all changes */
    ALL,

    /* PRICES allow to publish only the prices */
    PRICES;

    @JsonCreator
    public static PublishScope ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }

}
