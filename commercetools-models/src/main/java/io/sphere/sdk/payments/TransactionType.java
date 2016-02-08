package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum TransactionType implements SphereEnumeration {
    AUTHORIZATION, CANCEL_AUTHORIZATION, CHARGE, REFUND, CHARGEBACK;

    @JsonCreator
    public static TransactionType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
