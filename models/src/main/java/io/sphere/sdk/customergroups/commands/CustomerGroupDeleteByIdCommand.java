package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

public class CustomerGroupDeleteByIdCommand extends DeleteByIdCommandImpl<CustomerGroup> {
    private CustomerGroupDeleteByIdCommand(final Versioned<CustomerGroup> customerGroup) {
        super(customerGroup, CustomerGroupEndpoint.ENDPOINT);
    }

    public static CustomerGroupDeleteByIdCommand of(final Versioned<CustomerGroup> customerGroup) {
        return new CustomerGroupDeleteByIdCommand(customerGroup);
    }
}
