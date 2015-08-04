package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ShipmentState implements SphereEnumeration {

    SHIPPED, READY, PENDING, PARTIAL, BACKORDER;

    @JsonCreator
    public static ShipmentState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
