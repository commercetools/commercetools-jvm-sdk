package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerDeleteCommandTest#execution()}
 *
 * @see Customer
 */
public interface CustomerDeleteCommand extends MetaModelReferenceExpansionDsl<Customer, CustomerDeleteCommand, CustomerExpansionModel<Customer>>, DeleteCommand<Customer> {
    static CustomerDeleteCommand of(final Versioned<Customer> versioned) {
        return new CustomerDeleteCommandImpl(versioned);
    }
}
