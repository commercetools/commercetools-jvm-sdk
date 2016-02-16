package io.sphere.sdk.customers.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

/**
 * A customer endpoint specific error, the account with the given credentials have not been found.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandIntegrationTest#execution()}
 */
public final class CustomerInvalidCredentials extends SphereError {
    public static final String CODE = "InvalidCredentials";

    @JsonCreator
    private CustomerInvalidCredentials(final String message) {
        super(CODE, message);
    }

    public static CustomerInvalidCredentials of(final String message) {
        return new CustomerInvalidCredentials(message);
    }
}
