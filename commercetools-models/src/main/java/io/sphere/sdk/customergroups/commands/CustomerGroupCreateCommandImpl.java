package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;

final class CustomerGroupCreateCommandImpl extends MetaModelCreateCommandImpl<CustomerGroup, CustomerGroupCreateCommand, CustomerGroupDraft, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupCreateCommand {
    CustomerGroupCreateCommandImpl(final MetaModelCreateCommandBuilder<CustomerGroup, CustomerGroupCreateCommand, CustomerGroupDraft, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }

    CustomerGroupCreateCommandImpl(final CustomerGroupDraft draft) {
        super(draft, ENDPOINT, CustomerGroupExpansionModel.of(), CustomerGroupCreateCommandImpl::new);
    }

    public static CustomerGroupCreateCommandImpl of(final CustomerGroupDraft draft) {
        return new CustomerGroupCreateCommandImpl(draft);
    }

    public static CustomerGroupCreateCommandImpl of(final String groupName) {
        return of(CustomerGroupDraft.of(groupName));
    }
}
