package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerDeleteCommandTest#execution()}
 *
 * @see Customer
 */
public interface CustomerDeleteCommand extends ByIdDeleteCommand<Customer>, MetaModelExpansionDsl<Customer, CustomerDeleteCommand, CustomerExpansionModel<Customer>> {
    static CustomerDeleteCommand of(final Versioned<Customer> versioned) {
        return new CustomerDeleteCommandImpl(versioned);
    }
}
