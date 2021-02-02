package io.sphere.sdk.customers.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

public final class CustomerWeakPassword extends SphereError {
    public static final String CODE = "WeakPassword";

    @JsonCreator
    private CustomerWeakPassword(final String message) {
        super(CODE, message);
    }

    public static CustomerWeakPassword of(final String message) {
        return new CustomerWeakPassword(message);
    }
}
