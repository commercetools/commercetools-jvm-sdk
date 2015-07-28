package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class CustomerUpdateCommandImpl extends UpdateCommandDslImpl<Customer, CustomerUpdateCommand> implements CustomerUpdateCommand {
    CustomerUpdateCommandImpl(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        super(versioned, updateActions, CustomerEndpoint.ENDPOINT, CustomerUpdateCommandImpl::new);
    }

    CustomerUpdateCommandImpl(final UpdateCommandDslBuilder<Customer, CustomerUpdateCommand> builder) {
        super(builder);
    }
}
