package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

public interface CustomerGroupDeleteCommand extends ByIdDeleteCommand<CustomerGroup> {
    static DeleteCommand<CustomerGroup> of(final Versioned<CustomerGroup> customerGroup) {
        return new CustomerGroupDeleteCommandImpl(customerGroup);
    }
}
