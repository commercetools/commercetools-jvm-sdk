package io.sphere.sdk.customers;

import io.sphere.sdk.models.SphereError;

/**
 * A customer endpoint specific error, the account with the given credentials have not been found.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandTest#execution()}
 */
public class CustomerInvalidCredentials extends SphereError {
    public static final String CODE = "InvalidCredentials";

    public CustomerInvalidCredentials(final String message) {
        super(CODE, message);
    }
}
