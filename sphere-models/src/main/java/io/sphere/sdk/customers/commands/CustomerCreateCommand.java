package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;

/**
 * Creates/signs up a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomer()}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomerWithCart()}
 */
public interface CustomerCreateCommand extends CreateCommand<CustomerSignInResult> {
    static CustomerCreateCommand of(final CustomerDraft draft) {
        return new CustomerCreateCommandImpl(draft);
    }
}
