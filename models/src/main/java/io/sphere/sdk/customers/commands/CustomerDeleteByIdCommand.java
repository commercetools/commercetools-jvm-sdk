package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerDeleteByIdCommandTest#execution()}
 */
public class CustomerDeleteByIdCommand extends DeleteByIdCommandImpl<Customer> {
    private CustomerDeleteByIdCommand(final Versioned<Customer> versioned) {
        super(versioned, CustomersEndpoint.ENDPOINT);
    }

    public static CustomerDeleteByIdCommand of(final Versioned<Customer> versioned) {
        return new CustomerDeleteByIdCommand(versioned);
    }
}
