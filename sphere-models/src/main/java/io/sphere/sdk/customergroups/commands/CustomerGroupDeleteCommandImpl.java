package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.models.Versioned;

final class CustomerGroupDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<CustomerGroup, CustomerGroupDeleteCommand, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupDeleteCommand {
    CustomerGroupDeleteCommandImpl(final Versioned<CustomerGroup> versioned) {
        super(versioned, CustomerGroupEndpoint.ENDPOINT, CustomerGroupExpansionModel.of(), CustomerGroupDeleteCommandImpl::new);
    }

    CustomerGroupDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<CustomerGroup, CustomerGroupDeleteCommand, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }
}
