package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum TransactionState implements SphereEnumeration {
    PENDING, SUCCESS, FAILURE;

    @JsonCreator
    public static TransactionState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
