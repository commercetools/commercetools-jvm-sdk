package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;


final class CustomerGroupUpdateCommandImpl extends UpdateCommandDslImpl<CustomerGroup, CustomerGroupUpdateCommand> implements CustomerGroupUpdateCommand {
    CustomerGroupUpdateCommandImpl(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        super(versioned, updateActions, ENDPOINT, CustomerGroupUpdateCommandImpl::new);
    }

    CustomerGroupUpdateCommandImpl(final UpdateCommandDslBuilder<CustomerGroup, CustomerGroupUpdateCommand> builder) {
        super(builder);
    }
}
