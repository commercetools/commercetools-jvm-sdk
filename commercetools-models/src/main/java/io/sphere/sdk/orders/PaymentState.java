package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * PaymentState.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum PaymentState implements SphereEnumeration {
    BALANCE_DUE, FAILED, PENDING, CREDIT_OWED, PAID;

    @JsonCreator
    public static PaymentState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
