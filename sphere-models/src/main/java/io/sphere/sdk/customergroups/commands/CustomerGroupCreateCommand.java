package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;

public interface CustomerGroupCreateCommand extends CreateCommand<CustomerGroup> {
    static CustomerGroupCreateCommand of(final CustomerGroupDraft draft) {
        return new CustomerGroupCreateCommandImpl(draft);
    }

    static CustomerGroupCreateCommand of(final String groupName) {
        return of(CustomerGroupDraft.of(groupName));
    }
}
