package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.expansion.CustomerSignInResultExpansionModel;

/**
 * Creates/signs up a customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomer()}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomerWithCart()}
 */
final class CustomerCreateCommandImpl extends MetaModelCreateCommandImpl<CustomerSignInResult, CustomerCreateCommand, CustomerDraft, CustomerSignInResultExpansionModel<CustomerSignInResult>> implements CustomerCreateCommand {
    CustomerCreateCommandImpl(final MetaModelCreateCommandBuilder<CustomerSignInResult, CustomerCreateCommand, CustomerDraft, CustomerSignInResultExpansionModel<CustomerSignInResult>> builder) {
        super(builder);
    }

    CustomerCreateCommandImpl(final CustomerDraft body) {
        super(body, CustomerEndpoint.ENDPOINT_SIGNIN_RESULT, CustomerSignInResultExpansionModel.of(), CustomerCreateCommandImpl::new);
    }
}
