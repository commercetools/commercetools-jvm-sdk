package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;


final class CustomerGroupUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<CustomerGroup, CustomerGroupUpdateCommand, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupUpdateCommand {
    CustomerGroupUpdateCommandImpl(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        super(versioned, updateActions, ENDPOINT, CustomerGroupUpdateCommandImpl::new, CustomerGroupExpansionModel.of());
    }

    CustomerGroupUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<CustomerGroup, CustomerGroupUpdateCommand, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }
}
