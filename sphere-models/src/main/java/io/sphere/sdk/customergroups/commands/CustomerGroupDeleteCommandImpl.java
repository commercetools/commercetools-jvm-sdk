package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

public class CustomerGroupDeleteCommandImpl extends ByIdDeleteCommandImpl<CustomerGroup> {
    private CustomerGroupDeleteCommandImpl(final Versioned<CustomerGroup> customerGroup) {
        super(customerGroup, CustomerGroupEndpoint.ENDPOINT);
    }

    public static DeleteCommand<CustomerGroup> of(final Versioned<CustomerGroup> customerGroup) {
        return new CustomerGroupDeleteCommandImpl(customerGroup);
    }
}
