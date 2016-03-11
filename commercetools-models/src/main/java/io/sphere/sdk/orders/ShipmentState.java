package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * ShipmentState.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 */
public enum ShipmentState implements SphereEnumeration {

    SHIPPED, READY, PENDING, PARTIAL, BACKORDER, DELAYED;

    @JsonCreator
    public static ShipmentState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
