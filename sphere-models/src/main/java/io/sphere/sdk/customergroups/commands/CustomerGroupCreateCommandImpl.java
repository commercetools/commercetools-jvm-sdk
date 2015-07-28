package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;

final class CustomerGroupCreateCommandImpl extends CreateCommandImpl<CustomerGroup, CustomerGroupDraft> implements CustomerGroupCreateCommand {
    CustomerGroupCreateCommandImpl(final CustomerGroupDraft draft) {
        super(draft, ENDPOINT);
    }

    public static CustomerGroupCreateCommandImpl of(final CustomerGroupDraft draft) {
        return new CustomerGroupCreateCommandImpl(draft);
    }

    public static CustomerGroupCreateCommandImpl of(final String groupName) {
        return of(CustomerGroupDraft.of(groupName));
    }
}
