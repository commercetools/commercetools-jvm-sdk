package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * ReturnShipmentState.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 */
public enum ReturnShipmentState implements SphereEnumeration {

    ADVISED, RETURNED, BACK_IN_STOCK, UNUSABLE;

    @JsonCreator
    public static ReturnShipmentState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
