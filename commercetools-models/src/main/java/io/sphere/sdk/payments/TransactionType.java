package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Transaction Type.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum TransactionType implements SphereEnumeration {
    AUTHORIZATION, CANCEL_AUTHORIZATION, CHARGE, REFUND, CHARGEBACK;

    @JsonCreator
    public static TransactionType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
