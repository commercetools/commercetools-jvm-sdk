package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

final class CustomerDeleteCommandImpl extends ByIdDeleteCommandImpl<Customer> implements CustomerDeleteCommand {
    CustomerDeleteCommandImpl(final Versioned<Customer> versioned) {
        super(versioned, CustomerEndpoint.ENDPOINT);
    }
}
