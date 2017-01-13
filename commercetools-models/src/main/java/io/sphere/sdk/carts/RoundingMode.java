package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * A rounding mode specifies how the platform should round monetary values.
 */
public enum RoundingMode implements SphereEnumeration {

    /**
     * Round half to even.
     * Default rounding mode used in IEEE 754 computing functions and operators.
     */
    HALF_EVEN,

    /**
     * Round half up.
     */
    HALF_UP,

    /**
     * Round half down.
     */
    HALF_DOWN;

    @JsonCreator
    public static RoundingMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}