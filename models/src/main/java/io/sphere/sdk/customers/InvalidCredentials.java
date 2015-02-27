package io.sphere.sdk.customers;

import io.sphere.sdk.models.SphereError;

public class InvalidCredentials extends SphereError {
    public static final String CODE = "InvalidCredentials";

    public InvalidCredentials(final String message) {
        super(CODE, message);
    }
}
