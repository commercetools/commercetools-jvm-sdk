package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.expansion.CustomerSignInResultExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Creates/signs up a customer.
 *
 * <h3 id="create-customer-without-cart">Example for creating a customer without a cart</h3>
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomer()}
 *
 * <h3 id="create-customer-with-cart">Example for creating a customer with a cart</h3>
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomerWithCart()}
 *
 * @see Customer
 */
public interface CustomerCreateCommand extends CreateCommand<CustomerSignInResult>, MetaModelReferenceExpansionDsl<CustomerSignInResult, CustomerCreateCommand, CustomerSignInResultExpansionModel<CustomerSignInResult>> {
    static CustomerCreateCommand of(final CustomerDraft draft) {
        return new CustomerCreateCommandImpl(draft);
    }
}
