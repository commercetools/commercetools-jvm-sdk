package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ReturnPaymentState implements SphereEnumeration {
    NON_REFUNDABLE, INITIAL, REFUNDED, NOT_REFUNDED;

    @JsonCreator
    public static ReturnPaymentState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
