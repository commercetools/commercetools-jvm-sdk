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
    private final AuthenticationMode authenticationMode;
    @Nullable
    private final String password;

    private SetAuthenticationMode(final AuthenticationMode authenticationMode, @Nullable final String password) {
        super("setAuthenticationMode");
        this.authenticationMode = authenticationMode;
        this.password = password;
    }

    public static SetAuthenticationMode of(final AuthenticationMode authenticationMode, @Nullable final String password) {
        return new SetAuthenticationMode(authenticationMode, password);
    }

    public AuthenticationMode getAuthenticationMode() {
        return authenticationMode;
    }

    @Nullable
    public String getPassword() {
        return password;
    }
}
