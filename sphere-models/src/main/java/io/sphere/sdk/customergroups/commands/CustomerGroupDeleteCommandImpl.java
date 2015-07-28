package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

final class CustomerGroupDeleteCommandImpl extends ByIdDeleteCommandImpl<CustomerGroup> implements CustomerGroupDeleteCommand {
    CustomerGroupDeleteCommandImpl(final Versioned<CustomerGroup> customerGroup) {
        super(customerGroup, CustomerGroupEndpoint.ENDPOINT);
    }
}
