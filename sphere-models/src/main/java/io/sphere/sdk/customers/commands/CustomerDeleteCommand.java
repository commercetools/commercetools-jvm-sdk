package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerDeleteCommandTest#execution()}
 */
public interface CustomerDeleteCommand extends ByIdDeleteCommand<Customer> {
    static DeleteCommand<Customer> of(final Versioned<Customer> versioned) {
        return new CustomerDeleteCommandImpl(versioned);
    }
}
