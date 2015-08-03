package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#changeEmail()}
 */
public class ChangeEmail extends UpdateActionImpl<Customer> {
    private final String email;

    private ChangeEmail(final String email) {
        super("changeEmail");
        this.email = email;
    }

    public static ChangeEmail of(final String email) {
        return new ChangeEmail(email);
    }

    public String getEmail() {
        return email;
    }
}
