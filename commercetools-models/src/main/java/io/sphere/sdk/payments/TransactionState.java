package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Transaction State.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum TransactionState implements SphereEnumeration {
    INITIAL, PENDING, SUCCESS, FAILURE;

    @JsonCreator
    public static TransactionState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
