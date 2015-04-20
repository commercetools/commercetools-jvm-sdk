package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;

public class CustomerGroupCreateCommand extends CreateCommandImpl<CustomerGroup, CustomerGroupDraft> {
    private CustomerGroupCreateCommand(final CustomerGroupDraft draft) {
        super(draft, ENDPOINT);
    }

    public static CustomerGroupCreateCommand of(final CustomerGroupDraft draft) {
        return new CustomerGroupCreateCommand(draft);
    }

    public static CustomerGroupCreateCommand of(final String groupName) {
        return of(CustomerGroupDraft.of(groupName));
    }
}
