package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.AuthenticationMode;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets authentication mode for the customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#setAuthenticationMode()}
 */
public final class SetAuthenticationMode extends UpdateActionImpl<Customer> {
    @Nullable
    private final AuthenticationMode authenticationMode;

    private SetAuthenticationMode(@Nullable final AuthenticationMode authenticationMode) {
        super("setAuthenticationMode");
        this.authenticationMode = authenticationMode;
    }

    public static SetAuthenticationMode of(@Nullable final AuthenticationMode authenticationMode) {
        return new SetAuthenticationMode(authenticationMode);
    }

    @Nullable
    public AuthenticationMode getAuthenticationMode() {
        return authenticationMode;
    }
}
