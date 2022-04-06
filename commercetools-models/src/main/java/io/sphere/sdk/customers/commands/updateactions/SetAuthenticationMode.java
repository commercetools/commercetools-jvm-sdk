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
    private final AuthenticationMode authMode;
    @Nullable
    private final String password;

    private SetAuthenticationMode(final AuthenticationMode authMode, @Nullable final String password) {
        super("setAuthenticationMode");
        this.authMode = authMode;
        this.password = password;
    }

    public static SetAuthenticationMode of(final AuthenticationMode authMode, @Nullable final String password) {
        return new SetAuthenticationMode(authMode, password);
    }

    public AuthenticationMode getAuthMode() {
        return authMode;
    }

    @Nullable
    public String getPassword() {
        return password;
    }
}
