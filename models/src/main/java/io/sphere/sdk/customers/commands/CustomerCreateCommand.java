package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;

/**
 * Creates/signs up a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomer()}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomerWithCart()}
 */
public class CustomerCreateCommand extends CreateCommandImpl<CustomerSignInResult, CustomerDraft> {
    public CustomerCreateCommand(final CustomerDraft body) {
        super(body, CustomersEndpoint.ENDPOINT_SIGNIN_RESULT);
    }
}
