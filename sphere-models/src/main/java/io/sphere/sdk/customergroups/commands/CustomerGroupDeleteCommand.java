package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

public class CustomerGroupDeleteCommand extends ByIdDeleteCommandImpl<CustomerGroup> {
    private CustomerGroupDeleteCommand(final Versioned<CustomerGroup> customerGroup) {
        super(customerGroup, CustomerGroupEndpoint.ENDPOINT);
    }

    public static DeleteCommand<CustomerGroup> of(final Versioned<CustomerGroup> customerGroup) {
        return new CustomerGroupDeleteCommand(customerGroup);
    }
}
