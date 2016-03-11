package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * OrderState.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum OrderState implements SphereEnumeration {
    OPEN, COMPLETE, CANCELLED, CONFIRMED;

    @JsonCreator
    public static OrderState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
