package io.sphere.sdk.customers.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.commands.CustomerChangePasswordCommand;
import io.sphere.sdk.models.errors.SphereError;

/**
 * A customer endpoint specific error which is issued when a {@link CustomerChangePasswordCommand}
 * is executed with an invalid current password {@link CustomerChangePasswordCommand#currentPassword}.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandIntegrationTest#invalidCurrentPassword()}
 */
public final class CustomerInvalidCurrentPassword extends SphereError {
    public static final String CODE = "InvalidCurrentPassword";

    @JsonCreator
    private CustomerInvalidCurrentPassword(final String message) {
        super(CODE, message);
    }

    public static CustomerInvalidCurrentPassword of(final String message) {
        return new CustomerInvalidCurrentPassword(message);
    }
}
