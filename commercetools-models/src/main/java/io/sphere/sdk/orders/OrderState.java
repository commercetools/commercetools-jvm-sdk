package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum OrderState implements SphereEnumeration {
    OPEN, COMPLETE, CANCELLED;

    @JsonCreator
    public static OrderState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
