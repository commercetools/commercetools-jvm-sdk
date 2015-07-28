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
final class CustomerCreateCommandImpl extends CreateCommandImpl<CustomerSignInResult, CustomerDraft> implements CustomerCreateCommand {
    CustomerCreateCommandImpl(final CustomerDraft body) {
        super(body, CustomerEndpoint.ENDPOINT_SIGNIN_RESULT);
    }
}
